import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//have the intercetction between square and circle cut out of the square
public class Lobby extends JPanel{
    private final int width;
    private final int height;
    private final int carpetWidth;
    private final Color red = new Color(122, 0, 0);

    public Lobby(int width,int height) {
        setLayout(null);
        this.width = width;
        this.height = height;
        carpetWidth = width/15;
        labelMaker("Slots",0,height-carpetWidth);
        labelMaker("Blackjack",carpetWidth*3,height-carpetWidth);
        labelMaker("Pai Gow Poker",width-carpetWidth,height-carpetWidth);
        labelMaker("Casino War",width-carpetWidth*4,height-carpetWidth);
    }   

    private void labelMaker(String text,int x,int y){
        JLabel label = new JLabel(text);
        int size = (text.equals("Slots")) ? 30 : ((text.equals("Blackjack") || text.equals("Casino War")) ? 15 : 10);
        label.setFont(new Font("Serif", Font.PLAIN, size));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBounds(x,y,carpetWidth,carpetWidth/2);
        add(label);
    }

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        Graphics2D p2 = (Graphics2D) p;//lets me do the rectangle and circle cutout

        //Background
        p.setColor(new Color(138, 109, 25));
        p.fillRect(0, 0, width, height);
        p.setColor(Color.BLACK);
        int tileNum = 40;
        for (int h = 0;h < tileNum*1.5;h++){
            for (int i = 0;i < tileNum*1.5;i++){
                drawTile(p, (int) (width/(tileNum*1.5)*i), (int) (width/(tileNum*1.5)*h) - ((i%2 == 0) ? 0 : (int) (width/(tileNum*1.5)/2)),(int) (width/(tileNum*2.5)));
            }
        }
        // drawTile(p2, 0, 0, 50);



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
        p.setColor(red);
        p.fillRect(0,height-carpetWidth,carpetWidth,carpetWidth);
        p.fillRect(carpetWidth*3,height-carpetWidth,carpetWidth,carpetWidth);
        p.fillRect(width-carpetWidth,height-carpetWidth,carpetWidth,carpetWidth);
        p.fillRect(width-carpetWidth*4,height-carpetWidth,carpetWidth,carpetWidth);
    }

    private void drawTile(Graphics p,int x,int y,int widthT){
        int [][][] polys = {{{x+widthT/2, y},{x+widthT/4, y+widthT/4},{x+widthT/2, y+widthT/2},{x+widthT/4*3, y+widthT/4}},
                            {{x, y+widthT/2},{x+widthT/4, y+widthT/4},{x+widthT/2, y+widthT/2},{x+widthT/2, y+widthT}},
                            {{x+widthT/2, y+widthT/2},{x+widthT/2, y+widthT},{x+widthT, y+widthT/2},{x+widthT/4*3, y+widthT/4}},
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

    private void drawCutout(Graphics2D p,int x,int y,String type){
        int widthC = (type.contains("full")) ? carpetWidth*2 : ((type.contains("left")) ? carpetWidth : carpetWidth*2);
        int heightC = (!type.contains("b")) ? carpetWidth : carpetWidth*2;
        Area rect = new Area(new Rectangle2D.Double(x,y,widthC,heightC));
        Area cir = new Area(new Ellipse2D.Double(x,y,carpetWidth*2,carpetWidth*2));
        if (type.contains("right")) {
            Area rect2 = new Area(new Rectangle2D.Double(x,y,carpetWidth,carpetWidth*2));
            rect.subtract(rect2);
        }
        if (type.contains("b")) {
            Area rect2 = new Area(new Rectangle2D.Double(x,y,carpetWidth*2,carpetWidth));
            rect.subtract(rect2);
        }
        rect.subtract(cir);
        p.fill(rect);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //idky but it goes over if the same width 
        Lobby lobby = new Lobby(1200-16,600);
        frame.setSize(1200,600);
        frame.setTitle("Casino Lobby");
        frame.setVisible(true);           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);   
        frame.setLocation(0,200);
        frame.setResizable(false);
        frame.add(lobby);
        System.out.println("hello");
    }
}