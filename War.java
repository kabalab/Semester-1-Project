import java.util.List;
import java.util.Scanner;

public class War implements Game {

    private Deck gameDeck;
    private List<Card> userHand;
    private List<Card> dealerHand;

    private int totalMoney;
    private int betAmount;
    private int dealerWins;
    private int playerWins;

    private boolean gameOver = false;

    private Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new War(1000);
        game.startGame();
    }

    public War(int startingMoney) {
        this.totalMoney = startingMoney;
    }


    @Override
    public void startGame() {
        gameDeck = new Deck();
        shuffleDeck();

        userHand = gameDeck.drawCard(26);
        dealerHand = gameDeck.drawCard(26);


        while (!isGameOver()) {
            playTurn();
        }

        endGame();
    }

    @Override
    public void playTurn() {
        if (totalMoney <= 0) {
            slowPrint("You're out of money!");
            gameOver = true;
            return;
        }


        while (true) {
            slowPrintnln("How much would you like to bet: $");
            betAmount = input.nextInt();

            if (betAmount > totalMoney) {
                slowPrint("You only have $" + totalMoney);
            } else {
                break;
            }
        }

        Card playerCard = userHand.get(0);
        Card dealerCard = dealerHand.get(0);

        slowPrint("Player: " + playerCard.getName());
        slowPrint("Dealer: " + dealerCard.getName());

        resolveBattle(playerCard, dealerCard);

        slowPrint("You have $" + totalMoney + " left\n");

        userHand.remove(0);
        dealerHand.remove(0);

        if (userHand.isEmpty()) {
            reshuffleHands();
        }
    }

    @Override
    public void endGame() {

        if (playerWins > dealerWins) {
            slowPrint("Player won the game!");
        } else if (dealerWins > playerWins) {
            slowPrint("Dealer won the game!");
        } else {
            slowPrint("It's a tie!");
        }
    }

    @Override
    public void shuffleDeck() {
        gameDeck.shuffleDeck();
    }

    @Override
    public List<Card> drawCards(int amount) {
        return gameDeck.drawCard(amount);
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    private void resolveBattle(Card playerCard, Card dealerCard) {

        if (playerCard.getValue() > dealerCard.getValue()) {
            slowPrint("Player wins");
            totalMoney += betAmount;
            playerWins++;

        } else if (playerCard.getValue() < dealerCard.getValue()) {
            slowPrint("Dealer wins");
            totalMoney -= betAmount;
            dealerWins++;

        } else {
            slowPrint("Tie, go to war!");

            while (true) {
                if (userHand.isEmpty()) reshuffleHands();

                playerCard = userHand.get(0);
                dealerCard = dealerHand.get(0);

                slowPrint("Player: " + playerCard.getName());
                slowPrint("Dealer: " + dealerCard.getName());

                if (playerCard.getValue() > dealerCard.getValue()) {
                    slowPrint("Player wins the war!");
                    totalMoney += betAmount;
                    playerWins++;
                    break;

                } else if (playerCard.getValue() < dealerCard.getValue()) {
                    slowPrint("Dealer wins the war!");
                    totalMoney -= betAmount;
                    dealerWins++;
                    break;

                } else {
                    slowPrint("Tie again, war again!");
                    userHand.remove(0);
                    dealerHand.remove(0);
                }
            }
        }
    }

    private void reshuffleHands() {
        gameDeck = new Deck();
        shuffleDeck();
        userHand = drawCards(26);
        dealerHand = drawCards(26);
    }

    public static void slowPrint(String str) {
        for (char c : str.toCharArray()) {
            System.out.print(c);
            sleep(40);
        }
        System.out.println();
    }

    public static void slowPrintnln(String str) {
        for (char c : str.toCharArray()) {
            System.out.print(c);
            sleep(40);
        }
    }

    public static void sleep(int time) {
        try { Thread.sleep(time); }
        catch (InterruptedException ignored) {}
    }
}
 
