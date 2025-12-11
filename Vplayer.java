import javax.swing.*;
public class Vplayer extends JPanel{
    private int x;
    private int y;
    private final int size;
    private final int width;
    private final int height;
    private final int[][][] redSpots;
    private final boolean [] visible = {true,false,false,false,false};

    public Vplayer(int px,int py,int psize,int width,int height,int [][] redy){
        x = px;
        y = py;
        size = psize;
        this.width = width;
        this.height = height;
        redSpots = new int[4][][];
        redSpots[0] = redy;
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
}
