/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tuio;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class JPanelWithBackground extends JPanel {
    private Image backgroundImage;

    public JPanelWithBackground() {
        // Load the background image (replace "background.jpg" with the actual file path)

        // Set basic frame properties
       // Center the frame

        // Make the frame visible
        
        setVisible(true);
    }

   @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       Image imageobj = new ImageIcon("assets/lvl2bg.png").getImage();

         g.drawImage(imageobj,0, 0, null);
    }

}
