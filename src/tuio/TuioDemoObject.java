package TUIO;

/*
 TUIO Java GUI Demo
 Copyright (c) 2005-2014 Martin Kaltenbrunner <martin@tuio.org>
 
 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files
 (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge,
 publish, distribute, sublicense, and/or sell copies of the Software,
 and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:
 
 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import TUIO.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TuioDemoObject extends TuioObject {

    private Shape square;
    public String category;
    public String imagePath;

    public TuioDemoObject(TuioObject tobj) {
        super(tobj);
        int size = TuioDemoComponent.object_size;
        square = new Rectangle2D.Float(-size / 2, -size / 2, size, size);

        AffineTransform transform = new AffineTransform();
        transform.translate(xpos, ypos);
        transform.rotate(angle, xpos, ypos);
        square = transform.createTransformedShape(square);
    }

    public void paint(Graphics2D g, int width, int height) {

        float Xpos = xpos * width;
        float Ypos = ypos * height;
        float scale = height / (float) TuioDemoComponent.table_size;

        AffineTransform trans = new AffineTransform();
        trans.translate(-xpos, -ypos);
        trans.translate(Xpos, Ypos);
      trans.scale(scale, scale);
        Shape s = trans.createTransformedShape(square);

        g.setPaint(Color.white);
        g.fill(s);
        g.setPaint(Color.white);
//        if (symbol_id == 1) {
//            //g.drawString("Nadder", Xpos - 10, Ypos);
//             Image imageobj = new ImageIcon("level1assets/bodyparts/1.png").getImage();
//             g.drawImage(imageobj,(int) Xpos - 10,(int) Ypos,50, 50, null);
//
//        } 
//else if (symbol_id == 2) {
//            g.drawString("Ali", Xpos - 10, Ypos);
//        }
//        g.drawString(symbol_id + "Moamen", Xpos - 10, Ypos);


        String folderPathlevel1assetsbodyparts = "level1assets/bodyparts";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel1assetsbodyparts = Paths.get(folderPathlevel1assetsbodyparts);
        
         String folderPathlevel1assetsprivatebodyparts = "level1assets/privatebodyparts";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel1assetsprivatebodyparts = Paths.get(folderPathlevel1assetsprivatebodyparts);
        
        
        
        
        
        
//         String folderPathlevel1assetsbodypartsnew = "level1assetsnew/bodyparts";
//
//        // Create a Path object for the folderlevel1assetsbodyparts
//        Path folderlevel1assetsbodypartsnew = Paths.get(folderPathlevel1assetsbodypartsnew);
//        
//         String folderPathlevel1assetsprivatebodypartsnew = "level1assetsnew/privatebodyparts";
//
//        // Create a Path object for the folderlevel1assetsbodyparts
//        Path folderlevel1assetsprivatebodypartsnew = Paths.get(folderPathlevel1assetsprivatebodypartsnew);
//        
        
        
        
        
        
        
        
        
        String folderPathlevel2assetsnegative = "level2assets/negative";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel2assetsnegative = Paths.get(folderPathlevel2assetsnegative);
        
        
        String folderPathlevel2assetspositive = "level2assets/positive";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel2assetspositive = Paths.get(folderPathlevel2assetspositive);
        
          String folderPathlevel3assetsirrelevant = "level3assets/irrelevant";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel3assetsirrelevant = Paths.get(folderPathlevel3assetsirrelevant);
        
        
        String folderPathlevel3assetsrelevant = "level3assets/relevant";

        // Create a Path object for the folderlevel1assetsbodyparts
        Path folderlevel3assetsrelevant = Paths.get(folderPathlevel3assetsrelevant);

        try {
            // Get a stream of all files in the folderlevel1assetsbodyparts
            DirectoryStream<Path> directoryStreamlevel1assetsbodyparts = Files.newDirectoryStream(folderlevel1assetsbodyparts);
            DirectoryStream<Path> directoryStreamlevel1assetsprivatebodyparts = Files.newDirectoryStream(folderlevel1assetsprivatebodyparts);
            DirectoryStream<Path> directoryStreamlevel2assetsnegative = Files.newDirectoryStream(folderlevel2assetsnegative);
            DirectoryStream<Path> directoryStreamlevel2assetspositive = Files.newDirectoryStream(folderlevel2assetspositive);
            DirectoryStream<Path> directoryStreamlevel3assetsirrelevant = Files.newDirectoryStream(folderlevel3assetsirrelevant);
            DirectoryStream<Path> directoryStreamlevel3assetsrelevant = Files.newDirectoryStream(folderlevel3assetsrelevant);
//            DirectoryStream<Path> directoryStreamlevel1assetsbodypartsnew = Files.newDirectoryStream(folderlevel1assetsbodypartsnew);
//            DirectoryStream<Path> directoryStreamlevel1assetsprivatebodypartsnew = Files.newDirectoryStream(folderlevel1assetsprivatebodypartsnew);

            if(symbol_id == 214){

                    Image imageobj = new ImageIcon("src/tuio/assets/Suzanna.jpg").getImage();
                    category = "Suzanna";
                    imagePath  = "src/tuio/assets/Suzanna.jpg";
                    // g.drawString("Suzanna", Xpos - 10, Ypos);
                  g.drawImage(imageobj,(int) Xpos,(int) Ypos,50,50, null);
                }
            if(symbol_id == 215){

                    Image imageobj = new ImageIcon("src/tuio/assets/Bassmalla.jpg").getImage();
                    category = "Bassmalla";
                    imagePath  = "src/tuio/assets/Bassmalla.jpg";
             //      g.drawString("Bassmalla", Xpos - 10, Ypos);

                    g.drawImage(imageobj,(int) Xpos,(int) Ypos,50,50, null);
                }
            
            
            
            if(symbol_id == 200){

                    System.out.println(angle);
                     //g.drawString("Suzanna", Xpos - 10, Ypos);
//                    g.drawImage(imageobjf,(int) Xpos,(int) Ypos,250,250, null);
                    
//                    AffineTransform originalTransform = g.getTransform();
//                    AffineTransform rotationTransform = new AffineTransform();
//        rotationTransform.rotate(angle, xpos, ypos);
//          g.setTransform(rotationTransform);

        // Draw the rotated image
         if (angle > 0 && angle <= (Math.PI/ 2)) {
             category = "Home";
             Image imageobjf = new ImageIcon("src/tuio/assets/Home.png").getImage();

             g.drawImage(imageobjf,(int) Xpos, (int)Ypos  , 300, 300, null);

            //g.drawString(category, Xpos - 10, Ypos);

             System.out.println(category);
        } else if (angle > (Math.PI/ 2) && angle <= (Math.PI)) {
             category = "Exit";
                         //g.drawString(category, Xpos - 10, Ypos);
                         
              Image imageobjf = new ImageIcon("src/tuio/assets/Exit.png").getImage();

             g.drawImage(imageobjf,(int) Xpos, (int)Ypos  , 300, 300, null);

             System.out.println(category);
        } else if (angle > Math.PI && angle <= ((3*Math.PI)/2)) {
             category = "login";
//                         g.drawString(category, Xpos - 10, Ypos);

             Image imageobjf = new ImageIcon("src/tuio/assets/login.png").getImage();

             g.drawImage(imageobjf,(int) Xpos, (int)Ypos  , 300, 300, null);

             System.out.println(category);
        }  else if ((angle >  (3 * Math.PI )/ 2) && angle < (2 * Math.PI)) {
             category = "signup";
                         //g.drawString(category, Xpos - 10, Ypos);
             Image imageobjf = new ImageIcon("src/tuio/assets/signup.png").getImage();

             g.drawImage(imageobjf,(int) Xpos, (int)Ypos  , 300, 300, null);
                         
            //System.out.println(category);
        } 
//- imageobjf.getWidth(null) / 5
//- imageobjf.getHeight(null) / 5
        // Restore the original transform to avoid affecting subsequent drawings
//        g.setTransform(originalTransform);

                    
                    
                    
                    
                }

            // Counter for index
            int index = 0;
            // Loop over the files in the folderlevel1assetsbodyparts
            for (Path filePath : directoryStreamlevel1assetsbodyparts) {
                // Process each file as needed
                
                if(symbol_id == index){

                    Image imageobj = new ImageIcon("level1assets/bodyparts/"+filePath.getFileName().toString()).getImage();
                    category = "x";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos,50,50, null);
                }

                // Increment the index
                index++;
            }
            directoryStreamlevel1assetsbodyparts.close();

            
             for (Path filePath : directoryStreamlevel1assetsprivatebodyparts) {
                // Process each file as needed
                
                if(symbol_id == index){
                    Image imageobj = new ImageIcon("level1assets/privatebodyparts/"+filePath.getFileName().toString()).getImage();
                    category = "y";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
                }

                // Increment the index
                index++;
            }
             directoryStreamlevel1assetsprivatebodyparts.close();
             
             for (Path filePath : directoryStreamlevel2assetsnegative) {
                // Process each file as needed
                
                if(symbol_id == index){
                    Image imageobj = new ImageIcon("level2assets/negative/"+filePath.getFileName().toString()).getImage();
                    category = "negative_touch";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
                }

                // Increment the index
                index++;
            }
             directoryStreamlevel2assetsnegative.close();
             
             
             for (Path filePath : directoryStreamlevel2assetspositive) {
                // Process each file as needed
                
                if(symbol_id == index){
                    Image imageobj = new ImageIcon("level2assets/positive/"+filePath.getFileName().toString()).getImage();
                    category = "positive_touch";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
                }

                // Increment the index
                index++;
            }
             directoryStreamlevel2assetspositive.close();
             
             
                 for (Path filePath : directoryStreamlevel3assetsirrelevant) {
                // Process each file as needed
                
                if(symbol_id == index){
                    Image imageobj = new ImageIcon("level3assets/irrelevant/"+filePath.getFileName().toString()).getImage();
                    category = "irrelevant_action";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
                }

                // Increment the index
                index++;
            }
             directoryStreamlevel3assetsirrelevant.close();
             
             
              for (Path filePath : directoryStreamlevel3assetsrelevant) {
                // Process each file as needed
                
                if(symbol_id == index){
                    Image imageobj = new ImageIcon("level3assets/relevant/"+filePath.getFileName().toString()).getImage();
                    category = "relevant_action";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
                }

                // Increment the index
                index++;
            }
             directoryStreamlevel3assetsrelevant.close();
            
             


             
             
             switch (symbol_id){
                 case 40:
                     Image imageobj = new ImageIcon("level1assetsnew/bodyparts/1.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                 case 41:
                     Image imageobj2 = new ImageIcon("level1assetsnew/bodyparts/13.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj2,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                 case 42:
                     Image imageobj3 = new ImageIcon("level1assetsnew/bodyparts/15.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj3,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                    
                    
                  case 43:
                     Image imageobj4 = new ImageIcon("level1assetsnew/bodyparts/19.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj4,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                    
                  case 44:
                     Image imageobj5 = new ImageIcon("level1assetsnew/bodyparts/2.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj5,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                    
                  case 45:
                     Image imageobj6 = new ImageIcon("level1assetsnew/bodyparts/31.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj6,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                    
                    
                 case 46:
                     Image imageobj7 = new ImageIcon("level1assetsnew/bodyparts/7.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj7,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                    
                 case 47:
                     Image imageobj8 = new ImageIcon("level1assetsnew/bodyparts/8.png").getImage();
                    category = "non_private_body_part";
                    g.drawImage(imageobj8,(int) Xpos,(int) Ypos,50,50, null);
                    break;
                
                 case 48:
                      Image imageobj9 = new ImageIcon("level1assetsnew/privatebodyparts/10.png").getImage();
                    category = "private_body_part";
                    g.drawImage(imageobj9,(int) Xpos,(int) Ypos, 50, 50, null);
                    
                case 49:
                      Image imageobj10 = new ImageIcon("level1assetsnew/privatebodyparts/11.png").getImage();
                    category = "private_body_part";
                    g.drawImage(imageobj10,(int) Xpos,(int) Ypos, 50, 50, null);
                     
             }
//             
//             
//              int indx=40;
//             for (Path filePath : directoryStreamlevel1assetsbodypartsnew) {
//                // Process each file as needed
//                
//                if(symbol_id == indx){
//
//                    Image imageobj = new ImageIcon("level1assetsnew/bodyparts/"+filePath.getFileName().toString()).getImage();
//                    category = "non_private_body_part";
//                    g.drawImage(imageobj,(int) Xpos,(int) Ypos,50,50, null);
//                }
//
//                // Increment the index
//                indx++;
//            }
//            directoryStreamlevel1assetsbodypartsnew.close();
//
//            
//             for (Path filePath : directoryStreamlevel1assetsprivatebodypartsnew) {
//                // Process each file as needed
//                
//                if(symbol_id == indx){
//                    Image imageobj = new ImageIcon("level1assetsnew/privatebodyparts/"+filePath.getFileName().toString()).getImage();
//                    category = "private_body_part";
//                    g.drawImage(imageobj,(int) Xpos,(int) Ypos, 50, 50, null);
//                }
//
//                // Increment the index
//                indx++;
//            }
//             directoryStreamlevel1assetsprivatebodypartsnew.close();
//             
             
             
             
             
             
             
             
             

            // Close the directory stream
        } catch (Exception e) {
            e.printStackTrace();
        }

//switch(symbol_id){
//    case 1 -> {
//        Image imageobj = new ImageIcon("level1assets/bodyparts/1.png").getImage();
//        g.drawImage(imageobj,(int) Xpos - 10,(int) Ypos, null);
//            }
//}

    }

    public void update(TuioObject tobj) {

        float dx = tobj.getX() - xpos;
        float dy = tobj.getY() - ypos;
        float da = tobj.getAngle() - angle;

        if ((dx != 0) || (dy != 0)) {
            AffineTransform trans = AffineTransform.getTranslateInstance(dx, dy);
            square = trans.createTransformedShape(square);
        }

        if (da != 0) {
            AffineTransform trans = AffineTransform.getRotateInstance(da, tobj.getX(), tobj.getY());
            square = trans.createTransformedShape(square);
        }

        super.update(tobj);
    }

}
