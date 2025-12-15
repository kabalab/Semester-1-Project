import java.util.List;

public interface Game {

    /** Starts the game */
    void startGame();

    /** Ends the game */
    void endGame();

    /** Plays one turn / round */
    void playTurn();


    /** Shuffles the game deck */
    void shuffleDeck();

    /**
     * Draws cards from the deck
     * @param numCards number of cards to draw
     * @return List of drawn cards
     */
    List<Card> drawCards(int numCards);
  
    boolean isGameOver();
}
