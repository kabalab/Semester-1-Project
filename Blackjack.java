import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class BlackJack implements Game {


    static JPanel contentPane = new JPanel();
    static JFrame frame = new JFrame();
    private static BlackJack game;

    static JLabel playerHandValueLabel;
    static JLabel dealerHandValueLabel;
    static JLabel totalBet;
    static JLabel totalMoney;
    static JLabel totalDebt;
    static JLabel lastWinner;

    static JButton hiddenDealerCard;


    private Deck gameDeck;
    private Player player;
    private Player dealer;
    private boolean gameOver = false;

    static int betAmount = 0;
    static int playerMoney = 1000;
    static int playerDebt = 0;
    static String winner = "None";


    public static void runBlackJack(){
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setVisible(true);

        game = new BlackJack();
        genMenu();
    }


    public static void click(JButton button) {
        switch (button.getText()) {

            case "START" -> game.startGame();

            case "Draw Card" -> slowPrint("Drawing card...");

            case "Finish Turn" -> game.playTurn();

            case "Bet $1" -> bet(1);
            case "Bet $10" -> bet(10);
            case "Bet $100" -> bet(100);
            case "All In" -> bet(playerMoney);

            case "Get a Loan" -> {
                playerMoney += 1000;
                playerDebt += 1000;
                totalMoney.setText("Current Balance: $" + playerMoney);
                totalDebt.setText("Current Debt: $" + playerDebt);
            }
        }
    }

    private static void bet(int amount) {
        if (playerMoney >= amount) {
            betAmount += amount;
            playerMoney -= amount;
            totalBet.setText("Current Bet: $" + betAmount);
            totalMoney.setText("Current Balance: $" + playerMoney);
        }
    }


    public static void genMenu() {
        contentPane.removeAll();
        contentPane.setLayout(null);

        JButton start = new JButton("START");
        start.setBounds(250, 150, 100, 50);
        start.addActionListener(e -> click(start));
        contentPane.add(start);

        contentPane.repaint();
    }


    public void genGame() {
        contentPane.removeAll();
        contentPane.setLayout(null);

        List<Card> userHand = gameDeck.drawCard(2);
        List<Card> dealerHand = gameDeck.drawCard(2);

        player = new Player("user", playerMoney, userHand);
        dealer = new Player("dealer", playerMoney, dealerHand);

        // Player cards
        drawRect(userHand.get(0).getName(), 0, 400, 100, 125);
        drawRect(userHand.get(1).getName(), 100, 400, 100, 125);

        // Dealer cards
        drawRect(dealerHand.get(0).getName(), 0, 0, 100, 125);
        hiddenDealerCard = drawRect("?", 100, 0, 100, 125);

        playerHandValueLabel = new JLabel(
                "Player hand value: " + getBlackjackHandValue(userHand)
        );
        playerHandValueLabel.setBounds(0, 350, 300, 30);
        contentPane.add(playerHandValueLabel);

        dealerHandValueLabel = new JLabel(
                "Dealer hand value: " + blackjackValue(dealerHand.get(0)) + " + ?"
        );
        dealerHandValueLabel.setBounds(0, 130, 300, 30);
        contentPane.add(dealerHandValueLabel);

        // Draw button
        JButton drawButton = drawRect("Draw Card", 0, 200, 150, 75);
        final int[] cardCount = {0};

        drawButton.addActionListener(e -> {
            click(drawButton);
            gameDeck.drawCard(1, userHand);

            drawRect(
                    userHand.get(userHand.size() - 1).getName(),
                    200 + (cardCount[0] * 100),
                    400, 100, 125
            );
            cardCount[0]++;

            playerHandValueLabel.setText(
                    "Player hand value: " + getBlackjackHandValue(userHand)
            );
        });

        // Finish turn
        JButton finish = drawRect("Finish Turn", 200, 125, 150, 60);
        finish.addActionListener(e -> click(finish));

        // Betting labels
        totalBet = new JLabel("Current Bet: $" + betAmount);
        totalMoney = new JLabel("Current Balance: $" + playerMoney);
        totalDebt = new JLabel("Current Debt: $" + playerDebt);
        lastWinner = new JLabel("Last Winner: " + winner);

        totalBet.setBounds(150, 300, 250, 30);
        totalMoney.setBounds(150, 270, 250, 30);
        totalDebt.setBounds(150, 240, 250, 30);
        lastWinner.setBounds(350, 25, 200, 30);

        contentPane.add(totalBet);
        contentPane.add(totalMoney);
        contentPane.add(totalDebt);
        contentPane.add(lastWinner);

        // Betting buttons
        JButton b1 = drawRect("Bet $1", 400, 125, 150, 75);
        JButton b10 = drawRect("Bet $10", 400, 200, 150, 75);
        JButton b100 = drawRect("Bet $100", 400, 275, 150, 75);
        JButton allIn = drawRect("All In", 400, 350, 150, 75);
        JButton loan = drawRect("Get a Loan", 400, 425, 150, 75);

        b1.addActionListener(e -> click(b1));
        b10.addActionListener(e -> click(b10));
        b100.addActionListener(e -> click(b100));
        allIn.addActionListener(e -> click(allIn));
        loan.addActionListener(e -> click(loan));

        contentPane.repaint();
    }


    @Override
    public void playTurn() {
        List<Card> dealerHand = dealer.getHand();

        contentPane.remove(hiddenDealerCard);
        drawRect(dealerHand.get(1).getName(), 100, 0, 100, 125);

        dealerHandValueLabel.setText(
                "Dealer hand value: " + getBlackjackHandValue(dealerHand)
        );

        int count = 0;
        while (getBlackjackHandValue(dealerHand) < 17) {
            gameDeck.drawCard(1, dealerHand);
            drawRect(
                    dealerHand.get(dealerHand.size() - 1).getName(),
                    200 + (count++ * 100),
                    0, 100, 125
            );
        }

        int p = getBlackjackHandValue(player.getHand());
        int d = getBlackjackHandValue(dealerHand);

        if (p > 21 || (d <= 21 && d > p)) winner = "Dealer";
        else if (p == d) winner = "Tie";
        else winner = "Player";

        if (winner.equals("Player")) playerMoney += betAmount * 2;
        if (winner.equals("Tie")) playerMoney += betAmount;

        genGame();
    }


    @Override
    public void startGame() {
        gameDeck = new Deck();
        gameDeck.shuffleDeck();
        betAmount = 0;
        genGame();
    }

    @Override public void endGame() { gameOver = true; }
    @Override public void shuffleDeck() { gameDeck.shuffleDeck(); }
    @Override public List<Card> drawCards(int n) { return gameDeck.drawCard(n); }
    @Override public boolean isGameOver() { return gameOver; }


    public static JButton drawRect(String name, int x, int y, int w, int h) {
        JButton b = new JButton(name);
        b.setBounds(x, y, w, h);
        contentPane.add(b);
        return b;
    }

    public static int getBlackjackHandValue(List<Card> hand) {
        int total = 0, aces = 0;
        for (Card c : hand) {
            total += blackjackValue(c);
            if (c.getName().equalsIgnoreCase("ace")) aces++;
        }
        while (total > 21 && aces-- > 0) total -= 10;
        return total;
    }

    public static int blackjackValue(Card c) {
        return switch (c.getName().toLowerCase()) {
            case "jack", "queen", "king" -> 10;
            case "ace" -> 11;
            default -> c.getValue();
        };
    }

    public static void slowPrint(String s) {
        for (char c : s.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(20); } catch (Exception ignored) {}
        }
        System.out.println();
    }
}
