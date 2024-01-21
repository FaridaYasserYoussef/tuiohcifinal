package TUIO;

/*
 TUIO Java Level2test Demo
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

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import TUIO.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.util.concurrent.CompletableFuture;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import tuio.Level1;
import tuio.Main1;
import tuio.NewJFrame;
import tuio.LevelThree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.imageio.ImageIO;
import tuio.Lvl2;
import tuio.Level2test;
import javax.sound.sampled.*;
import tuio.HomePanel;
import tuio.HomeTest;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;


public class TuioDemoComponent extends JComponent implements TuioListener {
    

	private Hashtable<Long,TuioDemoObject> objectList = new Hashtable<Long,TuioDemoObject>();
	private Hashtable<Long,TuioCursor> cursorList = new Hashtable<Long,TuioCursor>();
	private Hashtable<Long,TuioDemoBlob> blobList = new Hashtable<Long,TuioDemoBlob>();


	public static final int finger_size = 15;
	public static final int object_size = 60;
	public static final int table_size = 760;
        private JFrame frame;
        public String currentLevel = "Home";
	private boolean fullscreen = false;
        private GraphicsDevice device;
	private Cursor invisibleCursor;
        private  int window_width  = 1161;
	private  int window_height = 800;
        private ArrayList<Integer> ignoreList = new ArrayList<Integer>();
        private ArrayList<Integer> ignoreList1 = new ArrayList<Integer>();
        private ArrayList<Integer> level2Correct = new ArrayList<Integer>();
        private ArrayList<Integer> level2Incorrect = new ArrayList<Integer>();


        public boolean wonLevelOne = false;
	public boolean wonLevelTwo = false;
	public boolean wonLevelThree = false;
        public boolean authenticated = false;
        int correctAnswersLevelTwo = 0;
        int correctAnswersLevelOne =0;
        int selectionMade = 0;

	public static int width, height;
	private float scale = 1.0f;
	public boolean verbose = false;
        
        public TuioDemoComponent(){
            this.frame = new HomeTest();
            //this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            //device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new java.awt.Point(0, 0), "invisible cursor");
            setupWindow();
            showWindow();
//            openConfirmationServer();

//            startSocketServer();
//            captureAndSaveImage("C:\\Users\\user\\Downloads\\Face Recognition\\people");
        }
        
        
         public  void startSocketServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);  // Use an appropriate port number

            while (true) {
                Socket socket = serverSocket.accept();
                String currentLevell = getCurrentLevel(); // Implement this method accordingly

                sendData(socket, currentLevell);

//                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  String getCurrentLevel() {
        // Implement this method to retrieve the current level
        // For example, you can use a sensor, user input, or any other method
        return currentLevel;
    }

    private static void sendData(Socket socket, String data) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF(data);
    }
        
         public static void captureAndSaveImage(String directoryPath) {
        // Create a VideoCapture object to access the webcam
        VideoCapture videoCapture = new VideoCapture(0);

        // Check if the VideoCapture is open
        if (!videoCapture.isOpened()) {
            System.out.println("Error: Could not open webcam.");
            return;
        }

        // Create a Mat object to store the captured image
        Mat frame = new Mat();

        // Capture a single frame from the webcam
        if (videoCapture.read(frame)) {
            // Generate a unique filename for the image
            String fileName = "captured_image_" + System.currentTimeMillis() + ".png";

            // Save the image to the specified directory
            String filePath = directoryPath + fileName;
            Imgcodecs.imwrite(filePath, frame);

            System.out.println("Image captured and saved to: " + filePath);
        } else {
            System.out.println("Error: Could not capture image from the webcam.");
        }

        // Release the VideoCapture object
        videoCapture.release();
    }
			
	public void setSize(int w, int h) {
		super.setSize(w,h);
		width = w;
		height = h;
		scale  = height/(float)TuioDemoComponent.table_size;	
	}
	
	public void addTuioObject(TuioObject tobj) {
		TuioDemoObject demo = new TuioDemoObject(tobj);
                
		objectList.put(tobj.getSessionID(),demo);
                

		if (verbose) 
			System.out.println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());	
	}

	public void updateTuioObject(TuioObject tobj) {

		TuioDemoObject demo = (TuioDemoObject)objectList.get(tobj.getSessionID());

		demo.update(tobj);
                
		if (verbose) 
			System.out.println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel()); 	
	}
	
	public void removeTuioObject(TuioObject tobj) {
		objectList.remove(tobj.getSessionID());
	       
             if(ignoreList.contains(tobj.symbol_id) && currentLevel == "Two"){
                 
                 if(level2Incorrect.contains(tobj.symbol_id)){
                     correctAnswersLevelTwo++;
                     
                     level2Incorrect.remove(Integer.valueOf(tobj.symbol_id));
                 }
                 else if(level2Correct.contains(tobj.symbol_id)){
                     correctAnswersLevelTwo--;
                     level2Correct.remove(Integer.valueOf(tobj.symbol_id));
                 }
                 
                   ignoreList.remove(Integer.valueOf(tobj.symbol_id));
                }
             
             if(ignoreList1.contains(tobj.symbol_id) && currentLevel == "One"){
                   ignoreList1.remove(Integer.valueOf(tobj.symbol_id));
                }
             if(tobj.symbol_id == 200){
                 selectionMade = 1;
             }

		if (verbose) 
			System.out.println("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");	
	}

	public void addTuioCursor(TuioCursor tcur) {
	
		if (!cursorList.containsKey(tcur.getSessionID())) {
			cursorList.put(tcur.getSessionID(), tcur);
			repaint();
		}
		
		if (verbose) 
			System.out.println("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY());	
	}

	public void updateTuioCursor(TuioCursor tcur) {

		repaint();
		
		if (verbose) 
			System.out.println("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel()); 
	}
	
	public void removeTuioCursor(TuioCursor tcur) {
	
		cursorList.remove(tcur.getSessionID());	
		repaint();
		
		if (verbose) 
			System.out.println("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")"); 
	}

	public void addTuioBlob(TuioBlob tblb) {
		TuioDemoBlob demo = new TuioDemoBlob(tblb);
		blobList.put(tblb.getSessionID(),demo);
		
		if (verbose) 
			System.out.println("add blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle());	
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		
		TuioDemoBlob demo = (TuioDemoBlob)blobList.get(tblb.getSessionID());
		demo.update(tblb);
		
		if (verbose) 
			System.out.println("set blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getMotionSpeed()+" "+tblb.getRotationSpeed()+" "+tblb.getMotionAccel()+" "+tblb.getRotationAccel()); 	
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		blobList.remove(tblb.getSessionID());
		
		if (verbose) 
			System.out.println("del blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+")");	
	}
	
	
	public void refresh(TuioTime frameTime) {
		repaint();
	}
	
	public void paint(Graphics g) {
            	//super.update(g);

		update(g);
	}

	public void update(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	
//		g2.setColor(Color.white);
		//g2.fillRect(0,0,width,height);
	
		//int w = (int)Math.round(width-scale*finger_size/2.0f);
		//int h = (int)Math.round(height-scale*finger_size/2.0f);
		int w = (int)Math.round(width-scale*finger_size/2.0f);
		int h = (int)Math.round(height-scale*finger_size/2.0f);
		Enumeration<TuioCursor> cursors = cursorList.elements();
		while (cursors.hasMoreElements()) {
			TuioCursor tcur = cursors.nextElement();
			if (tcur==null) continue;
			ArrayList<TuioPoint> path = tcur.getPath();
			TuioPoint current_point = path.get(0);
			if (current_point!=null) {
				// draw the cursor path
				g2.setPaint(Color.blue);
				for (int i=0;i<path.size();i++) {
					TuioPoint next_point = path.get(i);
					g2.drawLine(current_point.getScreenX(w), current_point.getScreenY(h), next_point.getScreenX(w), next_point.getScreenY(h));
					current_point = next_point;
				}
			}
			
			// draw the finger tip
			g2.setPaint(Color.lightGray);
			int s = (int)(scale*finger_size);
			g2.fillOval(current_point.getScreenX(w-s/2),current_point.getScreenY(h-s/2),s,s);
			g2.setPaint(Color.black);
			g2.drawString(tcur.getCursorID()+"",current_point.getScreenX(w),current_point.getScreenY(h));
		}

		// draw the objects
		Enumeration<TuioDemoObject> objects = objectList.elements() ;
		while (objects.hasMoreElements()) {
			TuioDemoObject tobj = objects.nextElement();
                 if (tobj!=null) tobj.paint(g2, width,height);
                 if(tobj.symbol_id == 200){
                     tobj.paint(g2, width,height);
                                                       
                              if(selectionMade == 1){
                                changeBasedOnSpinner(tobj.category);
                              }
                            
                 }
        }


                   if(this.currentLevel == "Home"){
//                       System.out.print(" in home");
                       Enumeration<TuioDemoObject> objects1 = objectList.elements();
            while (objects1.hasMoreElements()) {
		TuioDemoObject tobj = objects1.nextElement();
                if (tobj!=null) tobj.paint(g2, width,height);
                
                if(tobj.category == "Suzanna"){
                    System.out.print(" Suzanna's Marker");
                    this.currentLevel = "Two";
                    destroyWindow();
                    this.frame = new Level2test();
                    setupWindow();
                    showWindow();
                }
                else if(tobj.category == "Suzanna"){
                    System.out.print(" Suzanna's Marker");

                    this.currentLevel = "Two";
                    destroyWindow();
                    this.frame = new Level2test();
                    setupWindow();
                    showWindow();
                }
                   
            }
                       
                   }


                        if(this.currentLevel == "One"){
                          startSocketServer();
                          this.wonLevelOne = wonLevelOne(g2);
                          if(this.wonLevelOne){
                      playSound("assets/winSound.wav");
                      System.out.println("you won level 1");
                      destroyWindow();
                      this.currentLevel = "Two";
                      this.frame = new Level2test();
                      setupWindow();
                      showWindow();
                      

                              
                              
           }

                        }
                        else if(this.currentLevel == "Two"){
//                          startSocketServer();
                           
                          this.wonLevelTwo = wonLevelTwo(g2);
                         if(this.wonLevelTwo){
                              playSound("assets/winSound.wav");
                               try {
            Socket socket = new Socket("localhost", 12345); // Replace with the correct host and port
            System.out.println("Connected to Python app.");

            // Send data to Python app
//            sendEndSignal(socket);

            // Close the socket when done
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
//                              triggerCommunicationWithServer(this.frame);
                              
                              
           }

                        }
                        else if(this.currentLevel == "Three"){
                            this.wonLevelThree = wonLevelThree(g2);
                            
           if(this.wonLevelThree){
                 playSound("tuio/assets/winSound.mp3");

          try {
            ServerSocket serverSocket = new ServerSocket(65435);
            System.out.println("Server listening on port 65435");
            
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected: " + socket.getInetAddress());
                    
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    boolean swipeOccurred = inputStream.readBoolean();
                   if(swipeOccurred){
                       destroyWindow();
                       frame = new Level1();
                       socket.close();
                   }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TuioDemoComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
                              
                              
           }

                        }
//		}		
	}
        
            private static boolean isObjectOnLeftHalf(TuioDemoObject component) {
        // Get the x-coordinate of the object relative to its parent container
        int xCoordinate = component.getScreenX(712);

        // Get the width of the parent container (in this case, the JFrame)
        int containerWidth = 712;

        // Check if the x-coordinate is less than half of the container's width
        return xCoordinate < containerWidth / 2;
    }
            
    
     
            
    private static boolean isObjectOnRightHalf(TuioDemoObject component) {
    // Get the x-coordinate of the object relative to its parent container
    int xCoordinate = component.getScreenX(712);

    // Get the width of the parent container (in this case, the JFrame)
    int containerWidth = 712;

    // Check if the x-coordinate plus the width of the object is greater than half of the container's width
    return xCoordinate + 50 > containerWidth / 2;
}
        


      private static boolean isObjectOnBottomHalf(TuioDemoObject component) {
    // Get the y-coordinate of the object relative to its parent container
    int containerHeight = 674;
    int yCoordinate = component.getScreenY(containerHeight);

    // Check if the y-coordinate is greater than half of the container's height
    return yCoordinate > containerHeight / 2;
}
      
 private void changeBasedOnSpinner(String category){
     
     if(category == "Exit" && selectionMade == 1 ){
         //System.out.print("Do you really want to exit");
         playAllSound("assets/Exit.wav");
         boolean dialogAnswer  = getDialogAnswer( );
         if(dialogAnswer){
             System.exit(0);
                      selectionMade = 0;

         }
        
     }
     if(category == "signup" && selectionMade == 1 && authenticated == false){
         //System.out.print("Do you really want to exit");
                  playAllSound("assets/signup.wav");

                   boolean dialogAnswer  = getDialogAnswer( );
         if(dialogAnswer){
             Authenticate("signup");
//             this.currentLevel = "signup";
//             this.authenticated = true;
//           destroyWindow();
//           this.frame = new HomeTest();
           selectionMade = 0;

//           setupWindow();
//           showWindow();
         }
         
     }
      if(category == "login" && selectionMade == 1 && authenticated == false){
         //System.out.print("Do you really want to exit");
                  playAllSound("assets/login.wav");

                   boolean dialogAnswer  = getDialogAnswer( );
         if(dialogAnswer){
                Authenticate("login");
//             this.currentLevel = "login";
//             this.authenticated = true;
//           destroyWindow();
//           this.frame = new Level2test();
           selectionMade = 0;

//           setupWindow();
//           showWindow();
         }
                  
         
     }
       if(category == "Home" && selectionMade == 1 ){
         //System.out.print("Do you really want to exit");
                  playAllSound("assets/homepage.wav");
                  
                       boolean dialogAnswer  = getDialogAnswer( );
         if(dialogAnswer){

         this.currentLevel = "Home";
           destroyWindow();
           this.frame = new HomeTest();
           selectionMade = 0;

           setupWindow();
           showWindow();
         }
     }
 }
 
 
 public void sendStartSignal(){
     
      try {
            // Create a socket to connect to the Python program
            Socket socket = new Socket("localhost", 5555);

            // Send a list [start_signal, user_Id, level] to the Python program
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("start,some_user_id,some_level");

            // Receive acknowledgment from the Python program (if needed)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String acknowledgment = in.readLine();
            System.out.println(acknowledgment);

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 }

private static boolean isObjectOnTopHalf(TuioDemoObject component) {
    int containerHeight = 674;
    // Get the y-coordinate of the object relative to its parent container
    int yCoordinate = component.getScreenY(containerHeight);

    // Check if the y-coordinate is less than half of the container's height
    return yCoordinate < containerHeight / 2;
}
        public boolean wonLevelOne(Graphics2D g2){
            Enumeration<TuioDemoObject> objects = objectList.elements();
            while (objects.hasMoreElements()) {
		TuioDemoObject tobj = objects.nextElement();
                if (tobj!=null) tobj.paint(g2, width,height);
                System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelOne));
                System.out.println(ignoreList1);
                if(tobj.category == "private_body_part" && isObjectOnRightHalf(tobj)&& isObjectOnBottomHalf(tobj)&& !ignoreList1.contains(tobj.symbol_id)){
                
                        correctAnswersLevelOne++;
                        playSound("assets/clap.wav");
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelOne));

//                        System.out.println(correctAnswersLevelTwo);
                        ignoreList1.add(tobj.symbol_id);

                }
                
                 else if(tobj.category == "private_body_part" && isObjectOnLeftHalf(tobj) || isObjectOnTopHalf(tobj) && correctAnswersLevelOne > 0 && !ignoreList1.contains(tobj.symbol_id)){
                       correctAnswersLevelOne--;
                       playSound("assets/lose.wav");
                       System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelOne));
//                       System.out.println("In Second condition");
                       ignoreList1.add(tobj.symbol_id);



                }
                 else if(tobj.category == "non_private_body_part" && !ignoreList1.contains(tobj.symbol_id)){
                     if (isObjectOnTopHalf(tobj)){
                        correctAnswersLevelOne++;
                        playSound("assets/clap.wav");
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
//                        System.out.println(correctAnswersLevelTwo);
                        ignoreList1.add(tobj.symbol_id);}
                     else if (isObjectOnLeftHalf(tobj)&& isObjectOnBottomHalf(tobj)){
                         correctAnswersLevelOne++;
                        playSound("assets/clap.wav");
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
//                        System.out.println(correctAnswersLevelTwo);
                        ignoreList1.add(tobj.symbol_id);
                     }


                }
                 else if(tobj.category == "non_private_body_part" && isObjectOnRightHalf(tobj)&& isObjectOnBottomHalf(tobj)  && correctAnswersLevelTwo > 0 && !ignoreList1.contains(tobj.symbol_id)){
                       correctAnswersLevelOne--;
                       playSound("assets/lose.wav");
//                       System.out.println(correctAnswersLevelTwo);
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
                       ignoreList1.add(tobj.symbol_id);



                }
            }
        
           return correctAnswersLevelOne == 10;

        }
        
            private static void triggerCommunicationWithServer(JFrame level2 ) {
        try {
            Socket socket = new Socket("localhost", 65435);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Send signal to start communication
            String signal = "start";
            outputStream.write(signal.getBytes());

            // Receive the boolean result from the server
            boolean result = dataInputStream.readBoolean();
            if(result == true){
                level2.setVisible(false);
                HomeTest mainPage = new HomeTest();
                mainPage.setVisible(true);
            }
            System.out.println("Received from server: " + result);

            // Close streams and socket
            dataInputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
            
            
    
    
     public static void openConfirmationServer() {
            try {
                // Specify the URL of the Django endpoint
                String url = "http://127.0.0.1:8000/confirm/confirm/";

                // Create a URL object
                URL apiUrl = new URL(url);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Check if the request was successful (status code 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    // Print the response
                    System.out.println("Response from Django app: " + response.toString());
                } else {
                    System.out.println("Failed to make the request. Response Code: " + responseCode);
                }

                // Close the connection
                connection.disconnect();
                
           
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }
            
            
  private static boolean getDialogAnswer() {

        try {
           
            
            // Rest of your logic after the delay
            Socket socket = new Socket("localhost", 49153);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Send signal to start communication
            String signal = "start";
            outputStream.write(signal.getBytes());

            // Receive the boolean result from the server
            boolean result = dataInputStream.readBoolean();

            System.out.println("Received from server: " + result);

            // Close streams and socket
            dataInputStream.close();
            outputStream.close();
            socket.close();
            return result;
        } catch (IOException  e) {
            e.printStackTrace();
            return false;
        }
    }
  
  
   private static boolean startGame() {

        try {
           
            
            // Rest of your logic after the delay
            Socket socket = new Socket("localhost", 5555);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Send signal to start communication
            String signal = "start";
            outputStream.write(signal.getBytes());

            // Receive the boolean result from the server
            boolean result = dataInputStream.readBoolean();

            System.out.println("Received from server: " + result);

            // Close streams and socket
            dataInputStream.close();
            outputStream.close();
            socket.close();
            return result;
        } catch (IOException  e) {
            e.printStackTrace();
            return false;
        }
    }

         
           public static String openGameServerAdmin() {
            try {
                // Specify the URL of the Django endpoint
                String url = "http://127.0.0.1:8000/game/admin/";

                // Create a URL object
                URL apiUrl = new URL(url);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Check if the request was successful (status code 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the server
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        return response.toString();
                    }
                } else {
                    System.out.println("Failed to make the request. Response Code: " + responseCode);
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
     
    }
//          public void sendEndSignal() {
//        try {
//            Socket socket = new Socket("localhost", 12345); // Replace with the correct host and port
//
//            // Send "end" to Python server
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write("end".getBytes());
//
//            // Close the socket
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }  
           
           
           private static void sendEndSignal(Socket socket) throws IOException {
        // Send "end" signal to Python app
        try (OutputStream outputStream = socket.getOutputStream()) {
            String data = "end";
            outputStream.write(data.getBytes());
            System.out.println("Sent 'end' signal to Python app.");
        }
    }
         
         private void Authenticate(String authstring) {
           
            try {
       
                
            Socket socket = new Socket("localhost", 65435);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Send a signal to the server
            String signal = "";
             if(authstring.equals("signup")){
                signal = "signup";
            }
            else if(authstring.equals("login")){
                signal = "login";
            }
            
            outputStream.write(signal.getBytes());

            // Receive and process the server response
            if (signal.equals("login")) {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                  // Read the data from the BufferedReader
            String receivedData = reader.readLine();
            
                System.out.println("Login Result: " + receivedData);
                if(receivedData.equals("True")){
                    Authenticate("login");
                }
                else if(receivedData.equals("Two")){
                    this.authenticated = true;
                    this.currentLevel = "Two";
                    destroyWindow();
                    this.frame = new Level2test();
                    setupWindow();
                    showWindow();
                    startGame();
                    try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

                    
//                    try{
//                    Socket socket2 = new Socket("localhost", 12345); // Replace with the correct host and port
//                    System.out.println("Connected to Python app.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
                }
                else if(receivedData.equals("One")){
                    this.authenticated = true;
                    this.currentLevel = "One";
                    destroyWindow();
                    this.frame = new Level1();
                    setupWindow();
                    showWindow();
//                    openGameServerStudent();
                        startGame();
                }
                    else if(receivedData.equals("admin")){
                    openGameServerAdmin();
                    this.authenticated = true;
                    this.currentLevel = "Home";
                    destroyWindow();
                    this.frame = new Level2test();
                    setupWindow();
                    showWindow();
                }
                
            } else if (signal.equals("signup")) {
                boolean signupResult = dataInputStream.readBoolean();
                // Process signup result
                System.out.println("Signup Result: " + signupResult);
                if(signupResult == true){
                    this.authenticated = true;
                     this.currentLevel = "login";
                     
                    destroyWindow();
                    this.frame = new Level2test();
                    setupWindow();
                    showWindow();
                    Authenticate("login");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
           
//        try {
//            Socket socket = new Socket("localhost", 65435);
//            OutputStream outputStream = socket.getOutputStream();
//            InputStream inputStream = socket.getInputStream();
//            DataInputStream dataInputStream = new DataInputStream(inputStream);
//

//            // Send signal to start communication
//            
//            String signal = "";
//            
//            if(authstring.equals("signup")){
//                signal = "signup";
//            }
//            else if(authstring.equals("login")){
//                signal = "login";
//            }
//            outputStream.write(signal.getBytes());
//
//            // Receive the boolean result from the server
//            boolean result = dataInputStream.readBoolean();
//           
//            System.out.println("Received from server: " + result);
//
//            // Close streams and socket
//            dataInputStream.close();
//            outputStream.close();
//            socket.close();
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
    }
         
         
         
         
         
         
        
        public boolean wonLevelTwo(Graphics2D g2){
            
//            ArrayList<TuioDemoObject> privateToCheckAgainst = new ArrayList<TuioDemoObject>();
//            ArrayList<TuioDemoObject> nonPrivateToCheckAgainst = new ArrayList<TuioDemoObject>();

            Enumeration<TuioDemoObject> objects = objectList.elements();
            while (objects.hasMoreElements()) {
		TuioDemoObject tobj = objects.nextElement();
                if (tobj!=null) tobj.paint(g2, width,height);
                   System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
                   System.out.println(ignoreList);
                   
                    if(tobj.category == "negative_touch" && isObjectOnLeftHalf(tobj) && !ignoreList.contains(tobj.symbol_id)){
                        correctAnswersLevelTwo++;
                        level2Correct.add(tobj.symbol_id);
                        
                        playSound("assets/clap.wav");
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));

//                        System.out.println(correctAnswersLevelTwo);
                        ignoreList.add(tobj.symbol_id);


                }
                 else if(tobj.category == "negative_touch" && isObjectOnRightHalf(tobj)  && correctAnswersLevelTwo > 0 && !ignoreList.contains(tobj.symbol_id)){
                       correctAnswersLevelTwo--;
                       level2Incorrect.add(tobj.symbol_id);

                       playSound("assets/lose.wav");
                       System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
//                       System.out.println("In Second condition");
                       ignoreList.add(tobj.symbol_id);



                }
                    
                 else if(tobj.category == "positive_touch" && isObjectOnRightHalf(tobj) && !ignoreList.contains(tobj.symbol_id)){
                        correctAnswersLevelTwo++;
                        level2Correct.add(tobj.symbol_id);
                        playSound("assets/clap.wav");
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
//                        System.out.println(correctAnswersLevelTwo);
                        ignoreList.add(tobj.symbol_id);


                }
                 else if(tobj.category == "positive_touch" && isObjectOnLeftHalf(tobj)  && correctAnswersLevelTwo > 0 && !ignoreList.contains(tobj.symbol_id)){
                       correctAnswersLevelTwo--;
                       level2Incorrect.add(tobj.symbol_id);
                       playSound("assets/lose.wav");
//                       System.out.println(correctAnswersLevelTwo);
                        System.out.println("The Current Score is " + String.valueOf(correctAnswersLevelTwo));
                       ignoreList.add(tobj.symbol_id);



                }
                 		
            }

                 
//		}
               return correctAnswersLevelTwo == 16;

        }
        
        
         public boolean wonLevelThree(Graphics2D g2){
            int correctAnswers = 0;
//            ArrayList<TuioDemoObject> privateToCheckAgainst = new ArrayList<TuioDemoObject>();
//            ArrayList<TuioDemoObject> nonPrivateToCheckAgainst = new ArrayList<TuioDemoObject>();

            Enumeration<TuioDemoObject> objects = objectList.elements();
            while (objects.hasMoreElements()) {
		TuioDemoObject tobj = objects.nextElement();
                    if(tobj.category == "irrelevant_action" && isObjectOnLeftHalf(tobj)){
                        correctAnswers++;
                        playSound("tuio/assets/clap.mp3");
                        
                }
                 else if(tobj.category == "irrelevant_action" && isObjectOnRightHalf(tobj) && correctAnswers >= 0 ){
                       correctAnswers--;
                       playSound("tuio/assets/lose.mp3");

                }
                    
                 else if(tobj.category == "relevant_action" && isObjectOnRightHalf(tobj)){
                        correctAnswers++;
                        playSound("tuio/assets/clap.mp3");
                }
                 else if(tobj.category == "relevant_action" && isObjectOnLeftHalf(tobj)  && correctAnswers >= 0){
                       correctAnswers--;
                       playSound("tuio/assets/lose.mp3");

                }
                 
                 
		}
               return correctAnswers == 21;

        }
         
//           @Override
//    public  void paintComponent(Graphics g){
//        super.paintComponent(g);
//        g.setColor(Color.WHITE);
//        g.fillRect(0,0,1161,737);
//        try{
//            BufferedImage img=ImageIO.read(new File("D:/TUIO/src/tuio/assets/lvl2bg.jpg"));
//            g.drawImage(img,0,0,this);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    
//    }
     
         
 public void setupWindow() {
    frame.setTitle("WELCOME");

    ImageIcon labelIcon = null;  // Declare ImageIcon outside of if-else blocks

    if (currentLevel.equals("Two")) {
        System.out.println("I am in level 2");
        frame.setSize(1161, 800);
        tuio.HomePanel panel = new tuio.HomePanel();
        labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/lvl2bg.png");
        JLabel label = new JLabel(labelIcon);
        frame.add(this);  // Add 'this' to the panel
        panel.add(label);
        frame.add(panel);
    } else if (currentLevel.equals("Home")) {
        frame.setTitle("Home");
        frame.setSize(1161, 800);
        tuio.HomePanel panel2 = new tuio.HomePanel();
        labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/homebg.png");
        JLabel label = new JLabel(labelIcon);
        frame.add(this);  // Add 'this' to the panel2
        panel2.add(label);
        frame.add(panel2);
    }
    else if (currentLevel.equals("login")) {
        frame.setTitle("Login");
        frame.setSize(1161, 800);
        tuio.HomePanel panel2 = new tuio.HomePanel();
        labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/Loginbg.png");
        JLabel label = new JLabel(labelIcon);
        frame.add(this);  // Add 'this' to the panel2
        panel2.add(label);
        frame.add(panel2);
    }
else if (currentLevel.equals("signup")) {
        frame.setTitle("Signup");
        frame.setSize(1161, 800);
        tuio.HomePanel panel2 = new tuio.HomePanel();
        labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/Signupbg.png");
        JLabel label = new JLabel(labelIcon);
        frame.add(this);  // Add 'this' to the panel2
        panel2.add(label);
        frame.add(panel2);
    }


    if (labelIcon != null) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image imageGetDimensions = labelIcon.getImage();
        window_width = imageGetDimensions.getWidth(null);
        window_height = imageGetDimensions.getHeight(null);

        frame.setLocationRelativeTo(null); // Center the JFrame on the screen
        frame.setVisible(true);
    }
}
         
         
         
         
         
         
         
//         public void setupWindow() {
//             
//             if(currentLevel == "Two"){
//                  frame.setTitle("WELCOME");
//                  System.out.print("I am in level 2");
//        frame.setSize(1161, 800);
//        
//        tuio.Panel panel = new tuio.Panel();
//        ImageIcon labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/lvl2bg.jpg");
//        JLabel label = new JLabel(labelIcon); 
//        frame.add(this);
//        
//        panel.add(label);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(panel);
//        Image imagegetdimensions = labelIcon.getImage();
//        window_width = imagegetdimensions.getWidth(null);
//        window_height = imagegetdimensions.getHeight(null);
//       //this.paintComponent(g);
//
//        // Do not call pack() if you have already set a fixed size
//        // pack();
//           //frame.add(this);
//           //frame.setComponentZOrder(this, 1);
//        frame.setLocationRelativeTo(null); // Center the JFrame on the screen
//        frame.setVisible(true);
//        
//        
//             }else if(currentLevel == "Home"){
//                 
//                 
//                   frame.setTitle("Home");
//        frame.setSize(1161, 800);
//        
//        tuio.HomePanel panel2 = new tuio.HomePanel();
//        ImageIcon labelIcon = new ImageIcon("D:/TUIO/src/tuio/assets/homebg.png");
//        JLabel label = new JLabel(labelIcon); 
//        frame.add(this);
//        
//        panel2.add(label);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(panel2);
//        Image imagegetdimensions = labelIcon.getImage();
//        window_width = imagegetdimensions.getWidth(null);
//        window_height = imagegetdimensions.getHeight(null);
//
//        frame.setLocationRelativeTo(null); // Center the JFrame on the screen
//        frame.setVisible(true);                
//             }
//	}
//         
         public void destroyWindow() {
	
		frame.setVisible(false);
		if (fullscreen) {
			device.setFullScreenWindow(null);		
		}
		frame = null;
	}
	
	public void showWindow() {
	
		if (fullscreen) {
			int width  = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setSize(width,height);

			frame.setSize(width,height);
			//frame.setUndecorated(true);
			device.setFullScreenWindow(frame);
			frame.setCursor(invisibleCursor);
		} else {
			int width  = window_width;
			int height = window_height;
			this.setSize(width,height);
			
			frame.pack();
			Insets insets = frame.getInsets();			
			frame.setSize(width,height +insets.top);
			frame.setCursor(Cursor.getDefaultCursor());
		}
		
		frame.setVisible(true);
		frame.repaint();
	}
        
         public static void playSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    TuioDemoComponent.class.getResourceAsStream(soundFilePath));
             if (audioInputStream == null) {
            System.err.println("Error loading audio stream for: " + soundFilePath);
            return;
        }
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(1000);
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
    public static void playAllSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    TuioDemoComponent.class.getResourceAsStream(soundFilePath));
            
            if (audioInputStream == null) {
                System.err.println("Error loading audio stream for: " + soundFilePath);
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Add a LineListener to close the clip when it finishes playing
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
