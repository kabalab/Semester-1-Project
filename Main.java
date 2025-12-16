
import javax.swing.*;

/**
 * Application entry point that composes the casino lobby and movable player UI.
 * Starts the Swing UI on the Event Dispatch Thread and places the player inside
 * the lobby so the arrow keys control the player sprite.
 */
public class Main {
    /**
     * Launch the application UI. This method runs on the EDT.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int WIDTH = 1200 - 16;
            int HEIGHT = 600;

            JFrame frame = new JFrame();

            Lobby lobby = new Lobby(WIDTH, HEIGHT);

            // start player centered inside the lobby
            int startX = WIDTH / 2;
            int startY = HEIGHT / 2;
            Vplayer player = new Vplayer(
                    startX, startY, 50,
                    WIDTH, HEIGHT,
                    lobby.getRedSpots()
            );

            frame.setSize(1200, 600);
            frame.setTitle("Casino Lobby");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(0, 200);
            frame.setResizable(false);

            // put components in absolute positions and add the player as a child of the lobby
            frame.setLayout(null);
            lobby.setBounds(0, 0, WIDTH, HEIGHT);
            player.setBounds(0, 0, WIDTH, HEIGHT);

            frame.add(lobby);
            lobby.add(player); // add player into the lobby so it visually appears "in" it

            frame.setVisible(true);

            // ensure player receives key events
            SwingUtilities.invokeLater(player::requestFocusInWindow);
        });
    }
}
