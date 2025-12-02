import javax.swing.*;
public class Lobby extends JPanel{
    public Lobby() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Lobby lobby = new Lobby();
        frame.add(lobby);
        System.out.println("hello");
    }
}