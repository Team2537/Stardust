package frc.robot.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.input.HumanInput;
import frc.robot.input.Ports;

public class DriveSubsystem extends SubsystemBase{
    private static DriveSubsystem instance = null;

    private DriveMode currentDriveMode;

    private static CANSparkMax driveCANFrontLeft, driveCANFrontRight, driveCANBackLeft, driveCANBackRight;
    private static SpeedControllerGroup motorsControllerLeft, motorsControllerRight;

    private static DifferentialDrive driveDifferential;
    private static MecanumDrive driveMecanum;

    private static Solenoid driveSolFrontLeft, driveSolBackLeft, driveSolFrontRight, driveSolBackRight;


    public static final IdleMode DEFAULT_IDLE_MODE = IdleMode.kCoast;
    public static final MotorType DEFAULT_MOTOR_TYPE = MotorType.kBrushless;

    private static TalonSRX peanutFrontLeft, peanutFrontRight, peanutBackLeft, peanutBackRight;


    public DriveSubsystem(){
        // driveCANFrontLeft = new CANSparkMax(Ports.DRIVE_FRONT_LEFT, DEFAULT_MOTOR_TYPE);
        // driveCANBackLeft = new CANSparkMax(Ports.DRIVE_BACK_LEFT, DEFAULT_MOTOR_TYPE);
        // driveCANFrontRight = new CANSparkMax(Ports.DRIVE_FRONT_RIGHT, DEFAULT_MOTOR_TYPE);
        // driveCANBackRight = new CANSparkMax(Ports.DRIVE_BACK_RIGHT, DEFAULT_MOTOR_TYPE);
        // setIdleMode(DEFAULT_IDLE_MODE);
        // currentDriveMode = DriveMode.kMecanum;

        peanutFrontLeft = new TalonSRX(4);
        peanutFrontRight = new TalonSRX(3);
        peanutBackLeft = new TalonSRX(2);
        peanutBackRight = new TalonSRX(1);

        // driveSolFrontLeft = new Solenoid(Ports.DRIVE_SOL_FRONT_LEFT);
        // driveSolBackLeft = new Solenoid(Ports.DRIVE_SOL_BACK_LEFT);
        // driveSolFrontRight = new Solenoid(Ports.DRIVE_SOL_FRONT_RIGHT);
        // driveSolBackRight = new Solenoid(Ports.DRIVE_SOL_BACK_RIGHT);

        // motorsControllerLeft = new SpeedControllerGroup(driveCANFrontLeft, driveCANBackLeft);
        // motorsControllerRight = new SpeedControllerGroup(driveCANFrontRight, driveCANBackRight);

        // driveMecanum = new MecanumDrive(driveCANFrontLeft, driveCANBackLeft, driveCANFrontRight, driveCANBackRight);
        // driveDifferential = new DifferentialDrive(motorsControllerLeft, motorsControllerRight); 
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


    
	public void initDefaultCommand() {
		this.setDefaultCommand(new TempDriveCommand());
	}


    public void setPeanutLeft(double speed) {
        peanutFrontLeft.set(ControlMode.PercentOutput, -speed);
        peanutBackLeft.set(ControlMode.PercentOutput, -speed);
    }

    public void setPeanutRight(double speed) {
        peanutFrontRight.set(ControlMode.PercentOutput, speed);
        peanutBackRight.set(ControlMode.PercentOutput, speed);
    }

    public void killPeanutMotors() {
        setPeanutLeft(0);
        setPeanutRight(0);
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
        setMecanumDriveSpeed(Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_Y),
                             Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_X),
                             Robot.humanInput.getJoystickAxisLeft(HumanInput.AXIS_Z));
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

    public void setSolenoids(boolean state){
        driveSolFrontLeft.set(state);
        driveSolBackLeft.set(state);
        driveSolFrontRight.set(state);
        driveSolBackRight.set(state);
    }

    public void killDriveMotors(){
        setTankDrive(0, 0);
    }

    public boolean getSolenoids(){
        return driveSolFrontLeft.get();
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

    /**
     * Provides the default command
     */
    @Override
    public void periodic(){
        setDefaultCommand(new DriveCommand());
    }
}