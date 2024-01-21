/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tuio;

import javax.swing.JFrame;
import tuio.JPanelWithBackground;
/**
 *
 * @author user
 */
public class Lvl2 extends JFrame {
     JPanelWithBackground panel;
   public Lvl2(){
        panel = new JPanelWithBackground();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
}
