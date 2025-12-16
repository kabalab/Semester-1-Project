import java.awt.*;
import java.awt.event.ActionEvent;

public class Vplayer extends JPanel {

    private int x;
    private int y;
    private final int size;
    private final int width;
    private final int height;
    private final int[][][] redSpots = new int[4][][];
    private final boolean[] visible = {true,false,false,false,false};

    private int[] head;  // [x, y, size]
    private int[] body;  // [x, y, width, height]

    private final int SPEED = 10;

    // Constructor
    public Vplayer(int px, int py, int psize, int width, int height, int[][] redy) {
        x = px;
        y = py;
        size = psize;
        this.width = width;
        this.height = height;
        redSpots[0] = redy;

        updateParts();
        setupKeyBindings();
        setFocusable(true);
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
        repaint();
    }

    // ---------------- PLAYER LOGIC ----------------

    public boolean isVis(int room) {
        return visible[room % 4];
    }

    public void bound() {
        if (x - size / 2 < 0) x = size / 2;
        if (y - size < 0) y = size;
        if (x + size / 2 > width) x = width - size / 2;
        if (y + size > height) y = height - size;
    }

    private void updateParts() {
        head = new int[]{x - size / 2, y - size / 2, size / 2};
        body = new int[]{x - size / 4, y - size / 4, size / 2, size};
    }

    // ---------------- DRAWING ----------------

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        p.setColor(Color.BLUE);
        p.fillOval(head[0], head[1], head[2], head[2]);
        p.fillRect(body[0], body[1], body[2], body[3]);
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Vplayer player = new Vplayer(
                600, 150, 150,
                1200 - 16, 600,
                new int[][]{{0, 0, 0, 0}}
        );

        frame.setSize(1200, 600);
        frame.setTitle("Casino Lobby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 200);
        frame.setResizable(false);
        frame.add(player);
        frame.setVisible(true);
    }
}