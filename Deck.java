import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Class for deck objects
 * Handles creation, shuffling, and card drawing.
 */
public class Deck {

    // ============================
    // 🔹 Variables
    // ============================
    private int numCards;
    private List<Card> cards;


    // ============================
    // 🔹 Constructors
    // ============================

    /**
     * Creates a Deck object
     * @param numCards How many cards the deck has
     * @param cards An ArrayList of Card objects, act as the cards in the deck
     */
    public Deck(int numCards, List<Card> cards) {
        this.numCards = numCards;
        this.cards = cards;
    }

    /**
     * Creates a Standard 52 playing card deck
     */
    public Deck() {
        numCards = 52;
        String name = "";
        String suit = "";
        String color = "";

        List<Card> tempCards = new ArrayList<>();

        for (int i = 0; i < 4; i++) {          // Suits
            for (int j = 0; j < 13; j++) {     // Card values

                // ===== Names and values for non-face cards =====
                name = "" + (j + 2);
                int value = j + 2;

                // ===== Assigns names and values to face cards =====
                switch (j) {
                    case 9:
                        name = "Jack";
                        value = 11;
                        break;
                    case 10:
                        name = "Queen";
                        value = 12;
                        break;
                    case 11:
                        name = "King";
                        value = 13;
                        break;
                    case 12:
                        name = "Ace";
                        value = 14;
                        break;
                }

                // ===== Assigns suits =====
                switch (i) {
                    case 0:
                        suit = "❤";
                        color = "red";
                        break;
                    case 1:
                        suit = "♦️";
                        color = "red";
                        break;
                    case 2:
                        suit = "♠️";
                        color = "black";
                        break;
                    case 3:
                        suit = "♣️";
                        color = "black";
                        break;
                }

                // ===== Make card objects =====
                Card card = new Card(value, suit, color, name);

                // ===== Add cards to array =====
                tempCards.add(card);
            }
        }
        cards = tempCards;
    }


    // ============================
    // 🔹 Get Methods
    // ============================
    
    /**
     * Returns number of cards
     * @return int
     */
    public int getCardNum() {
        return numCards;
    }
    /**
     * Returns an ArrayList of all cards
     * @return List<Card>
     */
    public List<Card> getCards() {
        return cards;
    }


    // ============================
    // 🔹 Deck Methods
    // ============================

    /**
     * Shuffles the deck randomly
     * @return void
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Removes x cards from the top of deck returning all cards removed
     * @param x Amount of cards to take from the deck
     * @return List<Card>
     */
    public List<Card> drawCard(int x) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            drawnCards.add(cards.get(0));
            cards.remove(0);
        }
        return drawnCards;
    }

    /**
     * Removes x cards from the top of deck, adding all to a already existing List<Card>
     * @param x Amount of cards to take from the deck
     * @param hand ArrayList cards are added to
     * @return List<Card>
     */
    public List<Card> drawCard(int x, List<Card> hand) {
        for (int i = 0; i < x; i++) {
            hand.add(cards.get(0));
            cards.remove(0);
        }
        return hand;
    }
}
