import javax.swing.*;
import java.awt.*;

/**
 * A template Swing JFrame class for creating different button layouts.
 * 
 * This class can generate:
 * - A "wasd" control layout (with directional buttons and placeholders).
 * - A single button layout.
 * - A default empty panel if no recognized layout type is given.
 *
 * @author Kaleb
 * @version javax.swing template class
 */
public class Swing extends JFrame {

    /**
     * Creates a JButton with a background color no text and mouse hover/press effects.
     * * To make it call a certain function alter the handler method
     * by default will out put "name" not found
     *
     * @param clr The base color of the button
     * @param str The name of the button
     * @return The customized JButton
     */
    public JButton makeClrButton(Color clr,String str){
        JButton btn = new JButton(" ");
        btn.setName(str);
        btn.setBackground(clr);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = new Dimension(120, 40);
        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true); 
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(clr, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        btn.addActionListener(e -> handler(btn.getName()));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(lighten(clr,0.2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(clr);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(darken(clr, 0.2));
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(clr);
            }
        });

        return btn;
    }

    /**
     * Creates a JButton with a background color no text and mouse hover/press effects.
     * * To make it call a certain function alter the handler method
     * by default will out put "name" not found
     *
     * Background will defualt to 25, 90, 220 (blue)
     * 
     * @param str The name of the button
     * @return The customized JButton
     */
    public JButton makeClrButton(String str) {Color clr = new Color(25, 90, 220);return makeClrButton(clr,str);}

    /**
     * Creates a JButton with a background color no text and mouse hover/press effects.
     * * To make it call a certain function alter the handler method
     * by default will out put "name" not found
     *
     * Background will defualt to 25, 90, 220 (blue)
     * 
     * @return The customized JButton
     */
    public JButton makeClrButton() {Color clr = new Color(25, 90, 220);return makeClrButton(clr,"Color " + clr.getRed() + " " + clr.getGreen() + " " + clr.getBlue());}

    /**
     * Creates a JButton with text and mouse hover/press effects.
     * * To make it call a certain function alter the handler method
     * by default will out put "name" not found
     *
     * @param txt The text to display on the button
     * @param clr The color background on the button
     * @return The customized JButton
     */
    public JButton makeTxtButton(String txt,Color clr) {
        JButton btn = new JButton(txt);
        btn.setName(txt);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(clr);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = new Dimension(120, 40);
        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true); 
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(clr, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        btn.addActionListener(e -> handler(txt));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(lighten(clr, 0.2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(clr);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(darken(clr, 0.2));
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(clr);
            }
        });
        return btn;
    }

    /**
     * Creates a JButton with text and mouse hover/press effects.
     * * To make it call a certain function alter the handler method
     * by default will out put "name" not found.
     *
     * Background will defualt to 25, 90, 220 (blue)
     * 
     * @param txt The text to display on the button
     * @return The customized JButton
     */
    public JButton makeTxtButton(String txt) {return makeTxtButton(txt,new Color(25, 90, 220));}

    /**
     * Handles button actions based on the button's name. Needs to be preset to buttons.
     *
     * @param lbl The name of the button clicked
     */
    private void handler(String lbl) {
        switch (lbl) {
            case "Color 25 90 220" -> System.exit(0);
            default -> System.out.println(lbl + " function not found for button");
        }
    }

    /**
     * Lightens a given color by a factor.
     * *AI CREATED THIS FUNCTION
     * @param color  Base color
     * @param factor Amount to lighten (0.0 to 1.0)
     * @return The lightened color
     */
    public Color lighten(Color color, double factor) {
        int r = (int) Math.min(255, color.getRed()   + 255 * factor);
        int g = (int) Math.min(255, color.getGreen() + 255 * factor);
        int b = (int) Math.min(255, color.getBlue()  + 255 * factor);
        return new Color(r, g, b);
    }

    /**
     * Darkens a given color by a factor.
     * *AI CREATED THIS FUNCTION
     * @param color  Base color
     * @param factor Amount to darken (0.0 to 1.0)
     * @return The darkened color
     */
    public Color darken(Color color, double factor) {
        int r = (int) Math.max(0, color.getRed()   - 255 * factor);
        int g = (int) Math.max(0, color.getGreen() - 255 * factor);
        int b = (int) Math.max(0, color.getBlue()  - 255 * factor);
        return new Color(r, g, b);
    }
}