package frc.robot.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.input.HumanInput;
import frc.robot.input.Ports;

public class DriveSubsystem extends SubsystemBase{
    private static DriveSubsystem instance = null;

    private DriveMode currentDriveMode;

    private static CANSparkMax driveCANFrontLeft, driveCANFrontRight, driveCANBackLeft, driveCANBackRight;
    private static CANEncoder driveEncFrontLeft, driveEncFrontRight, driveEncBackLeft, driveEncBackRight;
    private static CANEncoder[] encArray;
    private static SpeedControllerGroup motorsControllerLeft, motorsControllerRight;

    private static DifferentialDrive driveDifferential;
    private static MecanumDrive driveMecanum;

    private static Solenoid drivePriSolFrontLeft, drivePriSolBackLeft, drivePriSolFrontRight, drivePriSolBackRight, driveSecSolFrontLeft, driveSecSolBackLeft, driveSecSolFrontRight, driveSecSolBackRight;


    public static final IdleMode DEFAULT_IDLE_MODE = IdleMode.kCoast;
    public static final MotorType DEFAULT_MOTOR_TYPE = MotorType.kBrushless;


    private DriveSubsystem(){
        driveCANFrontLeft = new CANSparkMax(Ports.DRIVE_FRONT_LEFT, DEFAULT_MOTOR_TYPE);
        driveCANBackLeft = new CANSparkMax(Ports.DRIVE_BACK_LEFT, DEFAULT_MOTOR_TYPE);
        driveCANFrontRight = new CANSparkMax(Ports.DRIVE_FRONT_RIGHT, DEFAULT_MOTOR_TYPE);
        driveCANBackRight = new CANSparkMax(Ports.DRIVE_BACK_RIGHT, DEFAULT_MOTOR_TYPE);
        setIdleMode(DEFAULT_IDLE_MODE);
        currentDriveMode = DriveMode.kMecanum;

        driveEncFrontLeft = new CANEncoder(driveCANFrontLeft);
        driveEncBackLeft = new CANEncoder(driveCANBackLeft);
        driveEncFrontRight = new CANEncoder(driveCANFrontRight);
        driveEncBackRight = new CANEncoder(driveCANBackRight);
        encArray = new CANEncoder[]{driveEncFrontLeft, driveEncBackLeft, driveEncFrontRight,
                                    driveEncBackRight};


        

        drivePriSolFrontLeft = new Solenoid(Ports.PRI_DRIVE_SOL_FRONT_LEFT);
        drivePriSolBackLeft = new Solenoid(Ports.PRI_DRIVE_SOL_BACK_LEFT);
        drivePriSolFrontRight = new Solenoid(Ports.PRI_DRIVE_SOL_FRONT_RIGHT);
        drivePriSolBackRight = new Solenoid(Ports.PRI_DRIVE_SOL_BACK_RIGHT);

        driveSecSolFrontLeft = new Solenoid(Ports.SEC_DRIVE_SOL_FRONT_LEFT);
        driveSecSolBackLeft = new Solenoid(Ports.SEC_DRIVE_SOL_BACK_LEFT);
        driveSecSolFrontRight = new Solenoid(Ports.SEC_DRIVE_SOL_FRONT_RIGHT);
        driveSecSolBackRight = new Solenoid(Ports.SEC_DRIVE_SOL_BACK_RIGHT);

        motorsControllerLeft = new SpeedControllerGroup(driveCANFrontLeft, driveCANBackLeft);
        motorsControllerRight = new SpeedControllerGroup(driveCANFrontRight, driveCANBackRight);

        driveMecanum = new MecanumDrive(driveCANFrontLeft, driveCANBackLeft, driveCANFrontRight, driveCANBackRight);
        driveDifferential = new DifferentialDrive(motorsControllerLeft, motorsControllerRight); 
        driveMecanum.setSafetyEnabled(false);
        driveDifferential.setSafetyEnabled(false);
    }


    /**
     * Singleton design pattern 
     * @return
     */

    public static DriveSubsystem getInstance(){
        if(instance == null) {
            instance = new DriveSubsystem();
        }
        return instance;
    }

    /**
    * Sets the motor speeds for the various drive types
    */
    public void setTankDrive(double percentOutputLeft, double percentOutputRight){
        driveDifferential.tankDrive(percentOutputLeft, percentOutputRight);
    }

    public void setTankDrive(){
        setTankDrive(Robot.humanInput.getJoystickAxisLeft(HumanInput.AXIS_Y),
                    Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_Y));
    }

    public void setMecanumDriveSpeed(double verticalSpeed, double horizontalSpeed, double zRotation){
        driveMecanum.driveCartesian(verticalSpeed, horizontalSpeed, zRotation);
    }

    public void setMecanumDriveSpeed(){
        setMecanumDriveSpeed(Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_X),
                             -Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_Y),
                             -Robot.humanInput.getJoystickAxisLeft(HumanInput.AXIS_Z));
    }

    /**
     * Setting things en masse, like solenoids and motors
     */
    public void setIdleMode(IdleMode mode){
        driveCANFrontLeft.setIdleMode(mode);
        driveCANBackLeft.setIdleMode(mode);
        driveCANFrontRight.setIdleMode(mode);
        driveCANBackRight.setIdleMode(mode);
    }

    public void resetEncoders(){
        for(CANEncoder i : encArray){
            i.setPosition(0);
        }
    }

    public void setSolenoids(boolean state, boolean antistate){
        drivePriSolFrontLeft.set(state);
        drivePriSolBackLeft.set(state);
        drivePriSolFrontRight.set(state);
        drivePriSolBackRight.set(state);

        driveSecSolFrontLeft.set(antistate);
        driveSecSolBackLeft.set(antistate);
        driveSecSolFrontRight.set(antistate);
        driveSecSolBackRight.set(antistate);

        System.out.println(drivePriSolBackLeft);
    }

    public void killDriveMotors(){
        setTankDrive(0, 0);
    }

    public boolean getSolenoids(){
        return drivePriSolFrontLeft.get();
    }

    /**
     * Defining a drive mode, along with a getter and a setter
     */
    public enum DriveMode{
        kMecanum(0), kTank(1);

        @SuppressWarnings("MemberName")
        public final int value;

        DriveMode(int value){
            this.value = value;
        }

        public static DriveMode fromID(int ID){
            if(ID == 1){
                return kTank;
            } 
            return kMecanum;
        }

    }

    public DriveMode getDriveMode(){
        return currentDriveMode;
    }

    public void setDriveMode(DriveMode mode){
        currentDriveMode = mode;
    }

    public void putEncodersToDash(){
        SmartDashboard.putNumber("Front Left Encoder", driveEncFrontLeft.getPosition());
        SmartDashboard.putNumber("Front Right Encoder", driveEncFrontRight.getPosition());
        SmartDashboard.putNumber("Back Left Encoder", driveEncBackLeft.getPosition());
        SmartDashboard.putNumber("BackRight Encoder", driveEncBackRight.getPosition());
    }

    public double getDistanceIn() {
        double distance = 0;
        
        return distance;
    }

    /**
     * Provides the default command
     */
    @Override
    public void periodic(){
        setDefaultCommand(new DriveCommand());
    }
}