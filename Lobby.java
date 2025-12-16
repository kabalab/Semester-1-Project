import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;//have the intercetction between square and circle cut out of the square
import java.awt.geom.Ellipse2D;//have the intercetction between square and circle cut out of the square
import java.awt.geom.Rectangle2D;//have the intercetction between square and circle cut out of the square
/**
 * Lobby panel that draws a casino themed layout.
 * Contains labeled carpet areas, tiled background, and curved cutouts.
 * This class handles rendering and layout only.
 */
public class Lobby extends JPanel{
    private final int width;
    private final int height;
    private final int carpetWidth;
    // number of tiles used for the tiled floor and randomized offsets
    private final int tileNum = 40;
    // deterministic random offsets computed once so the background doesn't "move" on repaint
    private final int ranX;
    private final int ranY;
    private final Color red = new Color(122, 0, 0);
    private final int[][] redSpots; // Stores the coordinates and sizes of the red rectangles

    /**
     * Creates the lobby panel with a fixed width and height.
     *
     * @param width total panel width
     * @param height total panel height
     */
    public Lobby(int width,int height) {
        setLayout(null);
        this.width = width;
        this.height = height;
        carpetWidth = width/15;

        // Label placement for each game area
        labelMaker("Slots",0,height-carpetWidth);
        labelMaker("Blackjack",carpetWidth*3,height-carpetWidth);
        labelMaker("Pai Gow Poker",width-carpetWidth,height-carpetWidth);
        labelMaker("Casino War",width-carpetWidth*4,height-carpetWidth);

        //Stores the values for red rectangle
        redSpots = new int[][] {{0,height-carpetWidth,carpetWidth,carpetWidth},
                                {carpetWidth*3,height-carpetWidth,carpetWidth,carpetWidth},
                                {width-carpetWidth,height-carpetWidth,carpetWidth,carpetWidth},
                                {width-carpetWidth*4,height-carpetWidth,carpetWidth,carpetWidth}
                                };

        // compute stable random offsets so tiles don't appear to move when the panel repaints
        double dx = width/(tileNum*2.5);
        ranX = (int) (Math.random()*(dx*2) - dx);
        ranY = (int) (Math.random()*(dx*2) - dx);
    }   

    /**
     * Expose the red interaction rectangles so other components (e.g. a player)
     * can detect and respond to entering them.
     *
     * @return array of {x,y,w,h} rectangles for the interactive spots
     */
    public int[][] getRedSpots() {
        return redSpots;
    }

    /**
     * Creates and places a centered JLabel at the given location.
     *
     * @param text display text
     * @param x x position
     * @param y y position
     */
    private void labelMaker(String text,int x,int y){
        JLabel label = new JLabel(text);
        //size is forced and stupid how done but i dont know how to make automated
        int size = (text.equals("Slots")) ? 30 : ((text.equals("Blackjack") || text.equals("Casino War")) ? 15 : 10);
        label.setFont(new Font("Serif", Font.PLAIN, size));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBounds(x,y,carpetWidth,carpetWidth/2);
        add(label);
    }

    /**
     * Paints the casino lobby graphics including background, tiles,
     * carpets, cutouts, and the interactive red rectangles.
     *
     * @param p base Graphics object for drawing
     */
    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        Graphics2D p2 = (Graphics2D) p;//lets me do the rectangle and circle cutout

        //Background

        // Background fill color and might want to change color because little ugly
        p.setColor(new Color(138, 109, 25));
        p.fillRect(0, 0, width, height);
        p.setColor(Color.BLACK);
        // Tile pattern offsets use the precomputed stable values
        for (int h = 0;h < tileNum*1.5;h++){
            for (int i = 0;i < tileNum*1.5;i++){
                drawTile(p, (int) (width/(tileNum*1.5)*i) + ranX, (int) (width/(tileNum*1.5)*h) - ((i%2 == 0) ? 0 : (int) (width/(tileNum*1.5)/2)) + ranY,(int) (width/(tileNum*2.5)));
            }
        }

         // Black carpet 
        p.setColor(Color.BLACK);
        p.fillRect(width/2-carpetWidth/2,0,carpetWidth,height/2);
        p.fillRect(carpetWidth/2,height/2,width-(carpetWidth),carpetWidth);
        p.fillArc(0,height/2,carpetWidth,carpetWidth,90,90);
        p.fillArc(width-carpetWidth,height/2,carpetWidth,carpetWidth,0,90);
        p.fillRect(0,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);
        p.fillRect(width-carpetWidth,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);
        p.fillRect(carpetWidth*3,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);
        p.fillRect(width-carpetWidth*4,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);

        //Curve part bewteen intercetions
        drawCutout(p2,carpetWidth,height/2+carpetWidth,"full");
        drawCutout(p2,carpetWidth*4,height/2+carpetWidth,"left");
        drawCutout(p2,width-carpetWidth*6,height/2+carpetWidth,"right");
        drawCutout(p2,width-carpetWidth*3,height/2+carpetWidth,"top");
        drawCutout(p2,width/2+carpetWidth/2,height/2-carpetWidth*2,"bleft");
        drawCutout(p2,width/2-carpetWidth/2*5,height/2-carpetWidth*2,"bright");

        //rectangles that need to be conntected to player
        p.setColor(red);
        p.fillRect(redSpots[0][0],redSpots[0][1],redSpots[0][2],redSpots[0][3]);
        p.fillRect(redSpots[1][0],redSpots[1][1],redSpots[1][2],redSpots[1][3]);
        p.fillRect(redSpots[2][0],redSpots[2][1],redSpots[2][2],redSpots[2][3]);
        p.fillRect(redSpots[3][0],redSpots[3][1],redSpots[3][2],redSpots[3][3]);
    }

    /**
     * Draws a three part polygon tile at the given position.
     * This produces a textured floor effect.
     *
     * @param p graphics object
     * @param x tile x position
     * @param y tile y position
     * @param widthT tile width
     */
    private void drawTile(Graphics p,int x,int y,int widthT){
        int [][][] polys = {{
                                {x+widthT/2, y},
                                {x+widthT/4, y+widthT/4},
                                {x+widthT/2, y+widthT/2},
                                {x+widthT/4*3, y+widthT/4}},
                            {
                                {x, y+widthT/2},
                                {x+widthT/4, y+widthT/4},
                                {x+widthT/2, y+widthT/2},
                                {x+widthT/2, y+widthT}},
                            {
                                {x+widthT/2, y+widthT/2},
                                {x+widthT/2, y+widthT},
                                {x+widthT, y+widthT/2},
                                {x+widthT/4*3, y+widthT/4}},
                            };
        int [][] colors = {{247, 0, 0},{92, 92, 92},{220, 220, 220}};
        Polygon poly;
        for (int polyn = 0;polyn < polys.length;polyn++){
            poly = new Polygon();
            for (int[] point : polys[polyn]) {//Hard to understand for loop but cool
                poly.addPoint(point[0], point[1]);
            }
            p.setColor(new Color(colors[polyn][0],colors[polyn][1],colors[polyn][2]));
            p.fillPolygon(poly);
        }
    }

    /**
     * Draws a rectangle and subtracts a circular section from it.
     * Creates curved transitions between carpet areas.
     *
     * @param p graphics object
     * @param x x coordinate of cutout
     * @param y y coordinate of cutout
     * @param type controls shape behavior such as left, right, full, top, bottom combos
     */
    private void drawCutout(Graphics2D p,int x,int y,String type){
        //changes the size of the "backround" square the gets the cutouts
        int widthC = (type.contains("full")) ? carpetWidth*2 : ((type.contains("left")) ? carpetWidth : carpetWidth*2);
        // b means bottom extension
        int heightC = (!type.contains("b")) ? carpetWidth : carpetWidth*2;
        Area rect = new Area(new Rectangle2D.Double(x,y,widthC,heightC));
        Area cir = new Area(new Ellipse2D.Double(x,y,carpetWidth*2,carpetWidth*2));
        // Additional rectangle to clip the laft side so that after the clip it doesnt stil show the left curve
        if (type.contains("right")) {
            Area rect2 = new Area(new Rectangle2D.Double(x,y,carpetWidth,carpetWidth*2));
            rect.subtract(rect2);
        }
        //same thing but cuts the top half to not allow the top to show in end rect
        if (type.contains("b")) {
            Area rect2 = new Area(new Rectangle2D.Double(x,y,carpetWidth*2,carpetWidth));
            rect.subtract(rect2);
        }
        rect.subtract(cir);
        p.fill(rect);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //gpt says it needs to be clipped a few pixels becuase frame borders add a few extra pixles wich is stupid
        Lobby lobby = new Lobby(1200-16,600);
        frame.setSize(1200,600);
        frame.setTitle("Casino Lobby");
        frame.setVisible(true);           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);   
        frame.setLocation(0,200);
        frame.setResizable(false);
        frame.add(lobby);
    }
}