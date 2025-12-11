import javax.swing.*;
import java.awt.*;
public class Vplayer extends JPanel{
    private int x;
    private int y;
    private final int size;
    private final int width;
    private final int height;
    private final int [][][] redSpots = new int[4][][];//[x][][] room [][x][] spot [][][x] x,y,width,height 
    private final boolean [] visible = {true,false,false,false,false};
    private final int [] head;//[0] x [1] y [2] size
    private final int [][] eyes; // [x][] eye [][0] x [][1] y [][2] size
    private final int [] body;//[0] x [1] y [2] width [3] height
    private final int [][] feet;// [x][] foot [][0] x [][1] y [][2] width [][3] height

    public Vplayer(int px,int py,int psize,int width,int height,int [][] redy){
        x = px;
        y = py;
        size = psize;
        this.width = width;
        this.height = height;
        redSpots[0] = redy;
        bound();
        head = new int[]{x - size/2,y-size/2,size/2};
        eyes = new int[][]{{}};
        body = new int[]{x-size/4,y-size/4,size/2,size};
        feet = new int[][]{{}};
    }

    public boolean isVis(int room){
        return visible[room % 4];
    }

    public void bound(){
        if (x-size/2 < 0) x = size/2;
        if (y - size < 0) y = size;
        if (x + size/2 > width) x = width - size/2;
        if (y + size > height) y = height - size;
    }

    @Override
    protected void paintComponent(Graphics p) {
        super.paintComponent(p);
        p.setColor(Color.blue);
        p.fillOval(head[0],head[1],head[2],head[2]);
        p.fillRect(body[0],body[1],body[2],body[3]);

        System.out.println(x + " " + y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //gpt says it needs to be clipped a few pixels becuase frame borders add a few extra pixles wich is stupid
        Vplayer player = new Vplayer(600,150,300,1200-16,600,new int[][]{{0,0,0,0}});
        frame.setSize(1200,600);
        frame.setTitle("Casino Lobby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);   
        frame.setLocation(0,200);
        frame.setResizable(false);
        frame.add(player);
        frame.setVisible(true);           
    }
}
