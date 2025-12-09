import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * BlackJack main class
 * Handles all top-level game logic, UI, and event handling.
 * Authors: Liam Bohonak, David Kostrinsky
 */
public class BlackJack {

    // ==============================
    // 🔹 Static Fields / Game State
    // ==============================
    static JPanel contentPane = new JPanel();
    static JFrame frame = new JFrame();

    static JLabel playerHandValueLabel = null;
    static JLabel dealerHandValueLabel = null;

    private static Deck gameDeck;
    private static JLabel totalBet;
    private static JLabel totalMoney;
    private static JLabel totalDebt;
    private static JLabel lastWinner;
    
    private static JButton hiddenDealerCard;
    
    private static Player player;
    private static Player dealer;

    static int betAmount = 0;
    static int playerMoney = 1000;
    static int playerDebt = 0;
    static String winner = "None";

    // ================================================================
    // 🔹 MAIN METHOD — Initializes the JFrame and launches main menu
    // ================================================================
    public static void main(String[] args) {
        // ===== Code starts here =====

        //Create JFrame
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Stuff in JFrames are actually displayed on the JPanel
        //We create a JPanel object to allow us to modify the JFrame easier
        //For the purpose of what we are doing, it will allow us to clear the JFrame easily
        //Whenever you want to add visual elements to the JFrame, add it to the panel instead
        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        //Creates game deck
        //Easier to program if this is made up here just roll with it
        gameDeck = new Deck();
        gameDeck.shuffleDeck();
        
        //Create start menu
        genMenu(frame, contentPane);
    }


    // ====================================
    // 🔹 CLICK HANDLER — umbrella method
    // ====================================
    public static void click(JButton button) {

        //Logic of what to do when each different button type is clicked
        //Basically an umbrella method that will call other methods
        //2nd level logic here
        //Once a button is clicked

        String text = button.getText();

        switch (text) {

            case "START":
                slowPrint("Starting game...");
                genGame();
                break;

            case "Draw Card":
                slowPrint("Drawing card...");
                //Actual logic is stored in genGame to avoid adding more parameters to this method
                break;

            case "Finish Turn":
                slowPrint("Dealers turn...");
                nextTurn();
                break;

            case "Bet $1":
                if (playerMoney >= 1) {
                    betAmount += 1;
                    playerMoney -= 1;
                    slowPrint("You have bet $" + betAmount + " in total...");
                    totalBet.setText("Current Bet : $" + betAmount);
                    totalMoney.setText("Current Balance: $" + playerMoney);
                } else {
                    slowPrint("You can't bet money you don't have...");
                }
                break;

            case "Bet $10":
                if (playerMoney >= 10) {
                    betAmount += 10;
                    playerMoney -= 10;
                    slowPrint("You have bet $" + betAmount + " in total...");
                    totalBet.setText("Current Bet : $" + betAmount);
                    totalMoney.setText("Current Balance: $" + playerMoney);
                } else {
                    slowPrint("You can't bet money you don't have...");
                }
                break;

            case "Bet $100":
                if (playerMoney >= 100) {
                    betAmount += 100;
                    playerMoney -= 100;
                    slowPrint("You have bet $" + betAmount + " in total...");
                    totalBet.setText("Current Bet : $" + betAmount);
                    totalMoney.setText("Current Balance: $" + playerMoney);
                } else {
                    slowPrint("You can't bet money you don't have...");
                }
                break;

            case "All In":
                if (playerMoney != 0) {
                    betAmount += playerMoney;
                    playerMoney = 0;
                    slowPrint("You have bet everything... Good luck...");
                } else {
                    slowPrint("You're already all in, stop clicking this button...");
                }
                totalBet.setText("Current Bet : $" + betAmount);
                totalMoney.setText("Current Balance: $" + playerMoney);
                break;
                
            case "Get a Loan":
                playerMoney += 1000;
                playerDebt += 1000;
                slowPrint("You took out a loan for $1000...You can always win it back later...");
                totalDebt.setText("Current Debt : $" + playerDebt);
                totalMoney.setText("Current Balance: $" + playerMoney);
                break;
                
            //Add more case break statements for future buttons
            default:
                System.out.println("Unknown button: " + text);
        }
    }


    // ==============================================
    // 🔹 Utility Methods (slowPrint, sleep, clear)
    // ==============================================

    // Prints a message letter by letter with delays
    public static void slowPrint(String strMessage) {
        char[] chars = strMessage.toCharArray();

        for (char c : chars) {
            System.out.print(c);
            sleep(20);
        }
        System.out.println();
    }

    // Sets a delay before the next action
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // ignored
        }
    }

    // Clears the console of all words
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    // ===========================================
    // 🔹 MENU GENERATION — Start + Settings menu
    // ===========================================
    public static void genMenu(JFrame frame, JPanel pane) {

        //Clear JFrame
        //Add elements
        //Make JButtons for main menu buttons

        pane.removeAll();
        pane.setLayout(null);

        // ===== START button =====
        JButton startButton = new JButton("START");
        startButton.setBounds(250, 150, 100, 50);

        //Action Listener
        startButton.addActionListener(new ActionListener() {

            //Override forces the program to give an error if the code below is wrong
            //Without it, if the code is wrong, we would click the button and nothing would happen
            @Override
            public void actionPerformed(ActionEvent e) {
                //Code that actually runs when button is clicked
                click(startButton);
            }
        });

        //Add buttons to JPanel
        pane.add(startButton);
        //pane.add(settingsButton);

        //Update JPanel
        pane.revalidate();
        pane.repaint();
    }


    // ============================================================
    // 🔹 GAME GENERATION — Creates cards, visuals, and game logic
    // ============================================================
    public static void genGame() {

        //Clear JFrame
        contentPane.removeAll();
        contentPane.setLayout(null);

        // ===== Deck creation and shuffling =====
        //this code is moved to main for convience of carrying deck values over between games

        // ===== Draw initial hands =====
        //Create two arrays and use drawCard method to fill each with
        //the top two cards of the deck (simulating starting hands)
        List<Card> userHand = gameDeck.drawCard(2);
        List<Card> dealerHand = gameDeck.drawCard(2);

        // ===== Player objects =====
        player = new Player("user", playerMoney, userHand);
        dealer = new Player("dealer", playerMoney, dealerHand);

        // ===== Visual setup =====
        //Player Cards
        JButton pCard0 = drawRect(userHand.get(0).getName(), 0, 400, 100, 125);
        JButton pCard1 = drawRect(userHand.get(1).getName(), 100, 400, 100, 125);
        playerHandValueLabel = new JLabel("Player hand value: " + BlackJack.getBlackjackHandValue(player.getHand()));
        playerHandValueLabel.setBounds(0, 350, 200, 50);
        contentPane.add(playerHandValueLabel);

        //Dealer Cards
        JButton dCard0 = drawRect(dealerHand.get(0).getName(), 0, 0, 100, 125);
        hiddenDealerCard = drawRect("?", 100, 0, 100, 125);
        dealerHandValueLabel = new JLabel("Dealer hand value: " + blackjackValue(dealerHand.get(0)) + " + ?");
        dealerHandValueLabel.setBounds(0, 110, 200, 50);
        contentPane.add(dealerHandValueLabel);

        // ===== Game Buttons =====
        JButton drawButton = drawRect("Draw Card", 0, 200, 150, 75);
        
        // ===== Reset Betting ====
        betAmount = 0;
        //Normally, only global and final variables can be referenced inside the action listener
        //However, if we use a final array then we can get around this by modifying the numbers in the array
        final int[] count = {0};

        //Card drawing logic
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Code that runs when button is clicked
                click(drawButton);
                gameDeck.drawCard(1, userHand);

                JButton pCardNew = drawRect(
                        userHand.get(userHand.size() - 1).getName(),
                        200 + (count[0] * 100),
                        400, 100, 125
                );
                count[0] += 1;

                playerHandValueLabel.setText("Total hand value: " + BlackJack.getBlackjackHandValue(player.getHand()));
            }
        });

        // ===== Finish Turn button =====
        JButton endButton = drawRect("Finish Turn", 200, 200, 150, 75);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gets new deck if 1/2 cards been drawn
                if (gameDeck.getCards().size() <= (int) (gameDeck.getCardNum() / 2)) {
                    gameDeck = new Deck();
                    gameDeck.shuffleDeck();
                } else {
                    gameDeck.shuffleDeck();
                }
                click(endButton);
            }
        });

        // ===== Betting Buttons and Counters =====
        totalBet = new JLabel("Current Bet: $" + betAmount);
        totalBet.setBounds(150, 300, 200, 75);
        contentPane.add(totalBet);

        totalMoney = new JLabel("Current Balance: $" + playerMoney);
        totalMoney.setBounds(150, 275, 200, 75);
        contentPane.add(totalMoney);
        
        totalDebt = new JLabel("Current Debt: $" + playerDebt);
        totalDebt.setBounds(150, 250, 200, 75);
        contentPane.add(totalDebt);
        
        lastWinner = new JLabel("Last Round Winner: " + winner);
        lastWinner.setBounds(400, 25, 200, 75);
        contentPane.add(lastWinner);

        JButton betButton1 = drawRect("Bet $1", 400, 125, 150, 75);
        betButton1.addActionListener(e -> click(betButton1));

        JButton betButton10 = drawRect("Bet $10", 400, 200, 150, 75);
        betButton10.addActionListener(e -> click(betButton10));

        JButton betButton100 = drawRect("Bet $100", 400, 275, 150, 75);
        betButton100.addActionListener(e -> click(betButton100));

        JButton betButtonAll = drawRect("All In", 400, 350, 150, 75);
        betButtonAll.addActionListener(e -> click(betButtonAll));
        
        JButton getLoan = drawRect("Get a Loan", 400, 425, 150, 75);
        getLoan.addActionListener(e -> click(getLoan));
    }


    // =========================================================
    // 🔹 NEXT TURN — Decides winners and starts next round
    // =========================================================
    public static void nextTurn() {
        boolean playerWin = false;
        boolean tie = false;
        List<Card> dealerHand = dealer.getHand();
        
        //Reveal Dealer hand
        //Remove old card
        contentPane.remove(hiddenDealerCard);
        contentPane.revalidate();
        contentPane.repaint();
        //Add new card
        drawRect(dealerHand.get(1).getName(), 100, 0, 100, 125);
        dealerHandValueLabel.setText("Dealer Hand Value: " + (BlackJack.getBlackjackHandValue(dealer.getHand())));
        //Apparently sleep causes all ui stuff to just stop, meaning it never visually updates
        //Run this line to force an update before sleeping
        contentPane.paintImmediately(contentPane.getBounds()); // Force UI update
        sleep(2000);
        
        //Check value of dealerHand, give extra cards if too small
        int count = 0;
        while (BlackJack.getBlackjackHandValue(dealer.getHand()) < 17) {
            count ++;
            gameDeck.drawCard(1, dealerHand);
            dealer.updateHand(dealerHand);
            dealerHandValueLabel.setText("Dealer Hand Value: " + (BlackJack.getBlackjackHandValue(dealer.getHand())));
            drawRect(dealerHand.get(dealerHand.size()-1).getName(), 100 + (100*count), 0, 100, 125);
            contentPane.paintImmediately(contentPane.getBounds()); // Force UI update
            sleep(2000);
        }
        sleep(2000);
        //Winning logic
        if (BlackJack.getBlackjackHandValue(player.getHand()) > 21) {
            playerWin = false;
            winner = "Dealer";
        }
        else if (BlackJack.getBlackjackHandValue(dealer.getHand()) > 21) {
            playerWin = true;
            winner = "Player";
        }
        else if (BlackJack.getBlackjackHandValue(dealer.getHand()) > BlackJack.getBlackjackHandValue(player.getHand())) {
            playerWin = false;
            winner = "Dealer";
        }
        else if (BlackJack.getBlackjackHandValue(dealer.getHand()) == BlackJack.getBlackjackHandValue(player.getHand())) {
            tie = true;
            winner = "None (Tie)";
        }
        else {
            playerWin = true;
            winner = "Player";
        }
        //What happens after winner decided
        if (tie) {
            playerMoney += betAmount;
            slowPrint("Tie...");
            genGame();
        }
        else if (playerWin) {
            playerMoney += (2 * betAmount);
            slowPrint("Player wins...");
            genGame();
        }
        else {
            slowPrint("Dealer wins...");
            genGame();
        }
    }


    // ==========================================
    // 🔹 DRAW RECT — creates a JButton rectangle
    // ==========================================
    public static JButton drawRect(String name, int xPos, int yPos, int width, int height) {
        JButton rect = new JButton(name);
        rect.setBounds(xPos, yPos, width, height);
        contentPane.add(rect);
        contentPane.revalidate();
        contentPane.repaint();
        return rect;
    }
    public static int getBlackjackHandValue(List<Card> hand) {
        int total = 0;
        int aceCount = 0;
    
        for (Card c : hand) {
            int val = blackjackValue(c);
            total += val;
    
            if (c.getName().toLowerCase().contains("ace")) {
                aceCount++;
            }
        }
    
        // Adjust aces from 11 → 1 if needed
        while (total > 21 && aceCount > 0) {
            total -= 10; // change an ace from 11 to 1
            aceCount--;
        }
    
        return total;
    }
    
    public static int blackjackValue(Card c) {
        String name = c.getName();
    
        if (name.equals("jack") || name.equals("queen") || name.equals("king")) {
            return 10;
        }
    
        // Ace starts as 11
        if (name.equals("ace")) {
            return 11;
        }
    
        // Otherwise return the normal number (2–10)
        return c.getValue();
    }
}
