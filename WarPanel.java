import java.awt.*;
import javax.swing.*;
/**
 * Simple panel used to host the Casino War UI. Currently this is a
 * placeholder panel that fills its area; it can be expanded with
 * interactive controls or graphics later.
 */
public class WarPanel extends JPanel{
    int width;
    int height;

    /**
     * Create a new WarPanel with the given size for rendering.
     *
     * @param width panel width
     * @param height panel height
     */
    public WarPanel(int width,int height){
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        p.fillRect(0,0,width,height);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //gpt says it needs to be clipped a few pixels becuase frame borders add a few extra pixles wich is stupid
        WarPanel WarPanel = new WarPanel(600-16,600);
        frame.setSize(600,600);
        frame.setTitle("Casino War");
        frame.setVisible(true);           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);   
        frame.setLocation(0,200);
        frame.setResizable(false);
        frame.add(WarPanel);
    }
}