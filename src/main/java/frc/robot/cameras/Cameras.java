package frc.robot.cameras;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.cscore.UsbCamera;


// import edu.wpi.cscore.VideoSource.ConnectionStrategy;


public class Cameras extends Thread {
    CameraServer camServer;
    UsbCamera cam0, cam1, cam2;
    static Cameras instance;
    public static int currentCam;

    private Cameras() {
        // Camera server manages all cameras and streams
        camServer = CameraServer.getInstance();
        // Start serving front camera (USB port 0)
        cam0 = camServer.startAutomaticCapture("cameras", 0);
         //CvSink cvz= camServer.getVideo();
        // Create additional camera(s)
        cam1   = new UsbCamera("cam1", 1);
        // creates another camera
        cam2   = new UsbCamera("cam2", 2);

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
            SmartDashboard.putString("CamSelection", "CLIMB");
        }else if(currentCam == 1){
            camServer.getServer().setSource(cam2);
            currentCam = 2;
            SmartDashboard.putString("CamSelection", "CONTROL PANEL");
        }else if(currentCam == 2){
            SmartDashboard.putString("CamSelection", "DRIVE");
            camServer.getServer().setSource(cam0);
            currentCam = 0;
        }
       
        // double joyx = Robot.humanInput.joystickRight.getX();
        // double joyy = Robot.humanInput.joystickRight.getY();

        // if(Math.signum(joyx) == 1){
        //     camServer.getServer().setSource(cam1);
        //     SmartDashboard.putString("CamSelection", "CLIMB");
        // }else if(Math.signum(joyx) == -1){
        //     camServer.getServer().setSource(cam2);
        //     SmartDashboard.putString("CamSelection", "CONTROL PANEL");
        // }else if(Math.signum(joyy) == 1){
        //     SmartDashboard.putString("CamSelection", "DRIVE");
        //     camServer.getServer().setSource(cam0);
        // }


    }

    // Run method is invoked when start method is called
    public void run() {
        while (true) {
            // run forever
        }
    }
}