import javax.swing.*;
import java.awt.*;
public class Lobby extends JPanel{
    private final int width;
    private final int height;
    private final int carpetWidth;
    private final Color red = new Color(122, 0, 0);

    public Lobby(int width,int height) {
        this.width = width;
        this.height = height;
        carpetWidth = width/15;
    }

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        p.setColor(Color.BLACK);
        p.fillRect(width/2-carpetWidth/2,0,carpetWidth,height/2);
        p.fillRect(carpetWidth/2,height/2,width-(carpetWidth),carpetWidth);
        p.fillArc(0,height/2,carpetWidth,carpetWidth,90,90);
        p.fillArc(width-carpetWidth,height/2,carpetWidth,carpetWidth,0,90);
        p.fillRect(0,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);
        p.fillRect(width-carpetWidth,height/2+carpetWidth/2,carpetWidth,height/2-carpetWidth/2);
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
        frame.setLocation(0,0);
        frame.setResizable(true);
        frame.add(lobby);
        System.out.println("hello");
    }
}