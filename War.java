import java.util.List;
import java.util.Scanner;

public class War
{
    private static Deck gameDeck;
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        gameDeck = new Deck();
        gameDeck.shuffleDeck();

        // War
        int money = 1000;
        int dealerWins = 0;
        int playerWins = 0;
        int betAmount = 0;
        List<Card> userHand = gameDeck.drawCard(26);
        List<Card> dealerHand = gameDeck.drawCard(26);
        while (true){
            if (money <= 0){
                slowPrint("You're out of money");
                break;
            }
            while (true){
                slowPrintnln("How much would you like to bet: $");
                betAmount = input.nextInt();
                if (betAmount > money){
                    slowPrint("You don't have that much, try again, you only have $" + money);
                } else {
                    break;
                }
            }
            slowPrint("Player: " + userHand.get(0).getName());
            slowPrint("Dealer: " + dealerHand.get(0).getName());
            if (userHand.get(0).getValue() > dealerHand.get(0).getValue()){
                slowPrint("Player wins");
                money += betAmount;
                playerWins++;

            } else if (userHand.get(0).getValue() < dealerHand.get(0).getValue()){
                slowPrint("Dealer wins");
                money -= betAmount;
                dealerWins++;

            } else {
                slowPrint("Tie, go to War!");
                while (true){
                    if (userHand.isEmpty()) {
                        gameDeck = new Deck();
                        gameDeck.shuffleDeck();
                        userHand = gameDeck.drawCard(26);
                        dealerHand = gameDeck.drawCard(26);
                    }
                    slowPrint("Player: " + userHand.get(0).getName());
                    slowPrint("Dealer: " + dealerHand.get(0).getName());
                    if (userHand.get(0).getValue() > dealerHand.get(0).getValue()){
                        slowPrint("Player wins the War");
                        money += betAmount;
                        playerWins++;
                        break;
                    } else if (userHand.get(0).getValue() < dealerHand.get(0).getValue()){
                        slowPrint("Dealer wins the War");
                        money -= betAmount;
                        dealerWins++;
                        break;
                    } else {
                        slowPrint("Tie, go to War! Again");
                        userHand.remove(0);
                        dealerHand.remove(0);
                    }
                }
            }
            slowPrint("You have $" + money + " left");
            betAmount = 0;
            userHand.remove(0);
            dealerHand.remove(0);
            if (userHand.isEmpty()){
                gameDeck = new Deck();
                gameDeck.shuffleDeck();
                userHand = gameDeck.drawCard(26);
                dealerHand = gameDeck.drawCard(26);
            }
            System.out.println();
        }
        if (playerWins > dealerWins){
            slowPrint("Player won the game");
        } else if (dealerWins > playerWins){
            slowPrint("Dealer won the game");
        } else {
            slowPrint("Tie!!!");
        }
        input.close();
    }
    
    public static void slowPrint(String strMessage) {
        char[] chars = strMessage.toCharArray();

        for (char c : chars) {
            System.out.print(c);
            sleep(50);
        }
        System.out.println();
    }
    
    public static void slowPrintnln(String strMessage) {
        char[] chars = strMessage.toCharArray();

        for (char c : chars) {
            System.out.print(c);
            sleep(50);
        }
    }
    
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // ignored
        }
    }
    
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
