package frc.robot.input;

public class Ports {
    public static int XBOX_CONTROLLER = 0;

    public static int DRIVE_FRONT_LEFT = 1;
    public static int DRIVE_BACK_LEFT = 2;
    public static int DRIVE_FRONT_RIGHT = 3;
    public static int DRIVE_BACK_RIGHT = 4;

    public static int PRI_DRIVE_SOL_FRONT_LEFT = 1;
    public static int PRI_DRIVE_SOL_BACK_LEFT = 3;
    public static int PRI_DRIVE_SOL_FRONT_RIGHT = 5;
    public static int PRI_DRIVE_SOL_BACK_RIGHT = 7;

    public static int SEC_DRIVE_SOL_FRONT_LEFT = 0;
    public static int SEC_DRIVE_SOL_BACK_LEFT = 2;
    public static int SEC_DRIVE_SOL_FRONT_RIGHT = 4;
    public static int SEC_DRIVE_SOL_BACK_RIGHT = 6; //back right secondary

    public static int TANKBUTTON = 1;

    public static int FLYWHEEL_PORT = 3; //flywheel speed
    public static int SOLENOID_PORT1 = 0;
    public static int SOLENOID_PORT2 = 4;
    public static int INTAKEBUTTON = 4; 
    
    public static final int LIDAR_COUNTER = 4; // DIO port for distance
    public static final int LIDAR_MODE = 5; // DIO port for lidar
    public static final int MOTOR_SHOOTER_PORT = 7; //Motor
    public static final int FEEDER_MOTOR_PORT = 5;
    public static final int BALL_DETECTOR_PORT = 2;
    public static final int INTAKE_DETECTOR_PORT = 1;
    public static final int TARGET_SPEED = -2000;
  }