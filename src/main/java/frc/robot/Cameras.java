package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
// import edu.wpi.cscore.VideoSource.ConnectionStrategy;


public class Cameras extends Thread {
    CameraServer   camServer;
    UsbCamera      cam0, cam1, cam2;
    static Cameras instance;
    public static int currentCam;

    private Cameras() {
        // Camera server manages all cameras and streams
        camServer = CameraServer.getInstance();
        // Start serving front camera (USB port 0)
        cam0 = camServer.startAutomaticCapture("camera 0", 0);
        // Create additional camera(s)
        cam1   = new UsbCamera("camera 1", 1);
        // creates another camera
        cam2   = new UsbCamera("camera 2", 2);
    

        // keep video streams open for fast switching
        //frontCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        //rearCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    }

    public static Cameras getInstance() {
       if (instance == null) {
           instance = new Cameras();
           instance.start();        
       }
       return instance;
    }


    public void startCameras() {
            camServer.getServer().setSource(cam0);
            currentCam = 0;
    }

    public void switchCameras(){
        if(currentCam == 0){
            camServer.getServer().setSource(cam1);
            currentCam = 1;
        }else if(currentCam == 1){
            camServer.getServer().setSource(cam2);
            currentCam = 2;
        }else if(currentCam == 2){
            camServer.getServer().setSource(cam0);
            currentCam = 0;
        }


    }

    // Run method is invoked when start method is called
    public void run() {
        while (true) {
            // run forever
        }
    }
}