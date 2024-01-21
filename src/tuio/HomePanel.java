/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tuio;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class HomePanel extends JPanel{
    private static  final int BOX_WIDTH=1161;
    private static  final int BOX_Height=800;
    @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
       // g.setColor(Color.WHITE);
        //g.fillRect(0,0,BOX_WIDTH,BOX_Height);
        
        
//        try{
//            BufferedImage img=ImageIO.read(new File("D:/TUIO/src/tuio/assets/lvl2bg.jpg"));
//            g.drawImage(img,0,0,this);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }

//ImageIcon backgroundImage = new ImageIcon("path/to/your/backgroundImage.jpg");
//backgroundImage.paintIcon(this, g, 0, 0);
    
    }
}
