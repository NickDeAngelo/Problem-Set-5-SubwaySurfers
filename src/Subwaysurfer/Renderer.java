//This class allows for Surfer to use double buffering and graphics, allowing for non-laggy playing.

package Subwaysurfer;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Subwaysurfer.Surfer.subwaySurfer.repaint(g);
    }
}
