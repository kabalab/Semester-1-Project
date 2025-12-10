import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for player objects
 * Purpose of player object is to store data about each player.
 * Authors: Liam Bohonak, David Kostrinsky
 */
public class Player {

    // ============================
    // 🔹 Variables
    // ============================
    private String name;
    private int money;

    // Creates an ArrayList to store cards the player has
    private List<Card> hand;


    // ============================
    // 🔹 Constructors
    // ============================
    public Player(String name, int money, List<Card> hand) {
        this.name = name;
        this.money = money;
        this.hand = hand;
    }


    // ============================
    // 🔹 Get Methods
    // ============================
    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public List<Card> getHand() {
        return hand;
    }


    // ============================
    // 🔹 Hand Value Calculation
    // ============================

    // Returns the total of all cards added up
    // Add ace logic here
    public int getHandValue() {
        int total = 0;
        int aceCount = 0;

        // Sum values of the cards
        for (Card card : hand) {
            total += card.getValue();
            if (card.getName().equals("ace")) {
                aceCount++;
            }
        }

        // Adjust for Aces if necessary
        while (total > 21 && aceCount > 0) {
            total -= 10;  // Ace is worth 1 instead of 11
            aceCount--;
        }

        return total;
    }
    
    //Updates player hand
    public void updateHand(List<Card> newHand) {
        hand = newHand;
    }
}
