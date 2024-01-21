import TUIO.TuioClient;
import TUIO.TuioDemoComponent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * TUIOApp class for testing sound playback.
 * 
 * @author user
 */
public class TUIOApp {
    
    public static void main(String argv[]) {
        
//        
//        
//        		MainPageDemo demo = new MainPageDemo();
//		TuioClient client = null;
// 
//		switch (argv.length) {
//			case 1:
//				try { 
//					client = new TuioClient( Integer.parseInt(argv[0])); 
//				} catch (Exception e) {
//					System.out.println("usage: java TuioDemo [port]");
//					System.exit(0);
//				}
//				break;
//			case 0:
//				client = new TuioClient();
//				break;
//			default: 
//				System.out.println("usage: java TuioDemo [port]");
//				System.exit(0);
//				break;
//		}
//		
//		if (client!=null) {
//			client.addTuioListener(demo.getTuioListener());
//			client.connect();
//		} else {
//			System.out.println("usage: java TuioDemo [port]");
//			System.exit(0);
//		}
//        
        
        
        
        
        
        

        TuioDemoComponent demo = new TuioDemoComponent();
        TuioClient client = null;

        switch (argv.length) {
            case 1:
                try { 
                    client = new TuioClient( Integer.parseInt(argv[0])); 
                } catch (Exception e) {
                    System.out.println("usage: java TuioDemo [port]");
                    System.exit(0);
                }
                break;
            case 0:
                client = new TuioClient();
                break;
            default: 
                System.out.println("usage: java TuioDemo [port]");
                System.exit(0);
                break;
        }

        if (client!=null) {
            client.addTuioListener(demo);
            client.connect();
        } else {
            System.out.println("usage: java TuioDemo [port]");
            System.exit(0);
        }
        
    }
}
