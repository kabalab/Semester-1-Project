import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class War
{
    private static Deck gameDeck;
    public static void main(String[] args){
        war();
    }
    public static int war() {
        Scanner input = new Scanner(System.in);
        gameDeck = new Deck();
        gameDeck.shuffleDeck();

        // War
        int totalMoney = 1000;
        int dealerWins = 0;
        int playerWins = 0;
        int betAmount = 0;
        List<Card> userHand = gameDeck.drawCard(26);
        List<Card> dealerHand = gameDeck.drawCard(26);
        while (true){
            if (totalMoney <= 0){
                slowPrint("You're out of totalMoney");
                break;
            }
            while (true){
                slowPrintnln("How much would you like to bet: $");
                betAmount = input.nextInt();
                if (betAmount > totalMoney){
                    slowPrint("You don't have that much, try again, you only have $" + totalMoney);
                } else {
                    break;
                }
            }
            slowPrint("Player: " + userHand.get(0).getName());
            slowPrint("Dealer: " + dealerHand.get(0).getName());
            if (userHand.get(0).getValue() > dealerHand.get(0).getValue()){
                slowPrint("Player wins");
                totalMoney += betAmount;
                playerWins++;

            } else if (userHand.get(0).getValue() < dealerHand.get(0).getValue()){
                slowPrint("Dealer wins");
                totalMoney -= betAmount;
                dealerWins++;

            } else {
                slowPrint("Tie, go to War!");
                while (true){
                    if (userHand.size() == 0) {
                        gameDeck = new Deck();
                        gameDeck.shuffleDeck();
                        userHand = gameDeck.drawCard(26);
                        dealerHand = gameDeck.drawCard(26);
                    }
                    slowPrint("Player: " + userHand.get(0).getName());
                    slowPrint("Dealer: " + dealerHand.get(0).getName());
                    if (userHand.get(0).getValue() > dealerHand.get(0).getValue()){
                        slowPrint("Player wins the War");
                        totalMoney += betAmount;
                        playerWins++;
                        break;
                    } else if (userHand.get(0).getValue() < dealerHand.get(0).getValue()){
                        slowPrint("Dealer wins the War");
                        totalMoney -= betAmount;
                        dealerWins++;
                        break;
                    } else {
                        slowPrint("Tie, go to War! Again");
                        userHand.remove(0);
                        dealerHand.remove(0);
                    }
                }
            }
            slowPrint("You have $" + totalMoney + " left");
            betAmount = 0;
            userHand.remove(0);
            dealerHand.remove(0);
            if (userHand.size() == 0){
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
        return totalMoney;
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
