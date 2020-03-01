package frc.robot.input;

public class Ports {
    //HUMAN INPUT
    public static int XBOX_CONTROLLER = 2;
    public static int JOYR = 1; 
    public static int JOYL = 0;

    //XBOX
    public static int PRESET_CLIMB_POSITION_BUTTON = 6; //checked //RB
    public static int ENABLE_CLIMB_BUTTON = 5; //checked //LB for both clim and winch
    public static int INTAKE_BUTTON = 4; //Y checked
    public static int SHOOTER_BUTTON = 1; //A //CHECKED
    public static int SPIN_X_TIMES_BUTTON = 3; //X checked
    public static int SPIN_TO_COLOR_BUTTON = 2; //B checked

    public static int STOP_SHOOTER_BUTTON = 7; //BACK checked
    public static int START_SHOOTER_BUTTON = 8; //START checked

    //JOYSTICK
    //LEFT
    public static int TANK_BUTTON = 1;
    public static int CAMERA_BUTTON = 6; //weird middle button
    //RIGHT


  
    //DRIVETRAIN SPARKMAXES
    public static int DRIVE_FRONT_LEFT = 1;
    public static int DRIVE_BACK_LEFT = 2;
    public static int DRIVE_FRONT_RIGHT = 3; 
    public static int DRIVE_BACK_RIGHT = 4;

    //DRIVETRAIN SOLENOIDS
    public static int PRI_DRIVE_SOL_FRONT_LEFT = 3;//1
    public static int PRI_DRIVE_SOL_BACK_LEFT = 4; //3
    public static int PRI_DRIVE_SOL_FRONT_RIGHT = 2; //5
    public static int PRI_DRIVE_SOL_BACK_RIGHT = 1; //7

    //INTAKE
    public static int FLYWHEEL_PORT = 8; //flywheel speed
    public static int SOLENOID_PORT1 = 0;
    //public static int SOLENOID_PORT2 = 4;
    
    //SHOOTER
    // public static final int LIDAR_COUNTER = 4; // DIO port for distance
    // public static final int LIDAR_MODE = 5; // DIO port for lidar
    public static final int MOTOR_SHOOTER_PORT = 5; //Motor
    public static final int FEEDER_MOTOR_PORT = 10;
    public static final int BALL_DETECTOR_PORT = 2;
    public static final int INTAKE_DETECTOR_PORT = 1;
    
    //CLIMB
    /*The buttons are...
    * Left Top - Enable Climb
    * Left Trigger - Winch
    * Right Top - Preset Position 
    * Right Joystick - Manual Telescope
    */
    public static int CLIMB_WINCH = 6;
    public static int CLIMB_TELESCOPE = 9;
    public static int TOP_SENSOR = 0; //DO WE HAVE ONE????!

    //CTRL PANEL
    public static int CW_MOTOR = 11; //port 5 hazlenut //port 11 on stardust
    public static double X_ROTATIONS_POWER = 1;
    public static double SPIN_TO_COLOR_POWER = .5;

    
  }