import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;

public class Vplayer extends JPanel {

    private int x;
    private int y;
    private final int size;
    private final int width;
    private final int height;
    // redSpots holds the interactive rectangles from the Lobby: {x,y,w,h} for each spot
    private final int[][] redSpots;
    // track whether the player is currently inside each spot to avoid repeated triggers
    private final boolean[] inSpot = new boolean[4];

    private int[] head;  // [x, y, size]
    private int[] body;  // [x, y, width, height]
    private int[][] feet; // [[x, y, width, height], ...]

    private final int SPEED = 10;

    // Constructor
    public Vplayer(int px, int py, int psize, int width, int height, int[][] redSpots) {
        x = px;
        y = py;
        size = psize;
        this.width = width;
        this.height = height;
        this.redSpots = redSpots;

        // transparent so Lobby can be seen underneath
        setOpaque(false);

        updateParts();
        setupKeyBindings();
        setFocusable(true);

        // check initial position
        checkInteractions();
    }

    // ---------------- MOVEMENT ----------------

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");

        am.put("left", new MoveAction(-SPEED, 0));
        am.put("right", new MoveAction(SPEED, 0));
        am.put("up", new MoveAction(0, -SPEED));
        am.put("down", new MoveAction(0, SPEED));
    }

    private class MoveAction extends AbstractAction {
        int dx, dy;

        MoveAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            movePlayer(dx, dy);
        }
    }

    private void movePlayer(int dx, int dy) {
        x += dx;
        y += dy;
        bound();
        updateParts();
        checkInteractions();
        repaint();
    }

    // ---------------- INTERACTIONS ----------------

    private void checkInteractions() {
        if (redSpots == null) return;

        Rectangle playerRect = new Rectangle(x - size / 4, y - size / 2, size / 2, size);

        for (int i = 0; i < Math.min(redSpots.length, inSpot.length); i++) {
            int[] r = redSpots[i];
            if (r == null || r.length < 4) continue;
            Rectangle redRect = new Rectangle(r[0], r[1], r[2], r[3]);
            boolean intersects = playerRect.intersects(redRect);
            if (intersects && !inSpot[i]) {
                inSpot[i] = true;
                enterSpot(i);
            } else if (!intersects && inSpot[i]) {
                inSpot[i] = false;
            }
        }
    }

    private void enterSpot(int idx) {
        // Run UI actions on the EDT
        SwingUtilities.invokeLater(() -> {
            String name;
            switch (idx) {
                case 0 -> name = "Slots";
                case 1 -> name = "Blackjack";
                case 2 -> name = "Pai Gow Poker";
                case 3 -> name = "Casino War";
                default -> name = "Unknown";
            }

            int res = JOptionPane.showConfirmDialog(this,
                    "Enter " + name + "?",
                    "Enter Game",
                    JOptionPane.YES_NO_OPTION);

            if (res == JOptionPane.YES_OPTION) {
                // Launch Blackjack for the Blackjack spot (index 1). Other games will show a message for now.
                if (idx == 1) {
                    BlackJack.runBlackJack();
                } else if (idx == 3) {
                    // Casino War exists as a console game; just inform user for now
                    JOptionPane.showMessageDialog(this, "Launching Casino War (console) - see console output.");
                    // start in background to avoid blocking EDT
                    new Thread(() -> War.main(new String[]{}), "war-thread").start();
                } else {
                    JOptionPane.showMessageDialog(this, name + " not implemented yet.");
                }
            }
        });
    }

    // ---------------- PLAYER LOGIC ----------------

    public boolean isVis(int room) {
        return visible[room % 4];
    }

    public void bound() {
        if (x - size / 4 < 0) x = size / 4;
        if (y - size/2 < 0) y = size/2;
        if (x + size / 4 > width) x = width - size / 4;
        if (y + size > height) y = height - size;
    }

    private void updateParts() {
        head = new int[]{x - size / 4, y - size / 2, size / 2};
        body = new int[]{x - size / 4, y - size / 4, size / 2, size-size/8};
        feet = new int[][]{{x - size / 4, y + size/2 + size / 8, size / 8, size / 8},
                           {x + size / 8,y + size/2 + size / 8, size / 8, size / 8}};
    }

    // ---------------- DRAWING ----------------

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        p.setColor(Color.BLUE);
        p.fillOval(head[0], head[1], head[2], head[2]);
        p.fillRect(body[0], body[1], body[2], body[3]);
        for (int[] foot : feet) {
            p.fillRect(foot[0], foot[1], foot[2], foot[3]);
        }
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        int WIDTH = 1200 - 16;
        int HEIGHT = 600;

        Lobby lobby = new Lobby(WIDTH, HEIGHT);
        Vplayer player = new Vplayer(
                600, 150, 50,
                WIDTH, HEIGHT,
                lobby.getRedSpots()
        );

        frame.setSize(1200, 600);
        frame.setTitle("Casino Lobby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 200);
        frame.setResizable(false);

        // use absolute positioning so both panels occupy the same space
        frame.setLayout(null);
        lobby.setBounds(0, 0, WIDTH, HEIGHT);
        player.setBounds(0, 0, WIDTH, HEIGHT);

        frame.add(lobby);
        frame.add(player); // added last so it's on top

        frame.setVisible(true);
    }
}