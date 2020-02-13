package frc.robot.drive;

import java.util.ArrayList;
import java.util.concurrent.atomic.DoubleAdder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
    private static CANEncoder[] sparkEncArray;
    
    private static SpeedControllerGroup motorsControllerLeft, motorsControllerRight;

    private static DifferentialDrive driveDifferential;
    private static MecanumDrive driveMecanum;

    private static Solenoid driveSolFrontLeft, driveSolBackLeft, driveSolFrontRight, driveSolBackRight;


    public static final IdleMode DEFAULT_IDLE_MODE = IdleMode.kCoast;
    public static final MotorType DEFAULT_MOTOR_TYPE = MotorType.kBrushless;

    // private static TalonSRX peanutFrontLeft, peanutFrontRight, peanutBackLeft, peanutBackRight;


    public DriveSubsystem(){
        driveCANFrontLeft = new CANSparkMax(Ports.DRIVE_FRONT_LEFT, DEFAULT_MOTOR_TYPE);
        driveCANBackLeft = new CANSparkMax(Ports.DRIVE_BACK_LEFT, DEFAULT_MOTOR_TYPE);
        driveCANFrontRight = new CANSparkMax(Ports.DRIVE_FRONT_RIGHT, DEFAULT_MOTOR_TYPE);
        driveCANBackRight = new CANSparkMax(Ports.DRIVE_BACK_RIGHT, DEFAULT_MOTOR_TYPE);
        setIdleMode(DEFAULT_IDLE_MODE);
        currentDriveMode = DriveMode.kMecanum;

        driveEncFrontLeft = new CANEncoder(driveCANFrontLeft);
        driveEncFrontRight = new CANEncoder(driveCANFrontRight);
        driveEncBackLeft = new CANEncoder(driveCANBackLeft);
        driveEncBackRight = new CANEncoder(driveCANBackRight);
        sparkEncArray = new CANEncoder[] {driveEncFrontLeft, driveEncFrontRight, driveEncBackLeft, driveEncBackRight};

        // peanutFrontLeft = new TalonSRX(4);
        // peanutFrontRight = new TalonSRX(3);
        // peanutBackLeft = new TalonSRX(2);
        // peanutBackRight = new TalonSRX(1);

        // peanutFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// peanutFrontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// peanutBackLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// peanutFrontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        driveSolFrontLeft = new Solenoid(Ports.DRIVE_SOL_FRONT_LEFT);
        driveSolBackLeft = new Solenoid(Ports.DRIVE_SOL_BACK_LEFT);
        driveSolFrontRight = new Solenoid(Ports.DRIVE_SOL_FRONT_RIGHT);
        driveSolBackRight = new Solenoid(Ports.DRIVE_SOL_BACK_RIGHT);

        motorsControllerLeft = new SpeedControllerGroup(driveCANFrontLeft, driveCANBackLeft);
        motorsControllerRight = new SpeedControllerGroup(driveCANFrontRight, driveCANBackRight);

        driveMecanum = new MecanumDrive(driveCANFrontLeft, driveCANBackLeft, driveCANFrontRight, driveCANBackRight);
        driveDifferential = new DifferentialDrive(motorsControllerLeft, motorsControllerRight); 
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
        setMecanumDriveSpeed(Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_Y),
                             Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_X),
                             Robot.humanInput.getJoystickAxisRight(HumanInput.AXIS_Z));
    }

    public void setPolarDriveSpeed(double magnitude, double angle, double zRotation){
        driveMecanum.drivePolar(magnitude, angle, zRotation);
    }

    /**
     * a whole bunch of encoder shit
     *
     */

    public void resetEncoders(){
        for (CANEncoder enc : sparkEncArray){
            enc.setPosition(0);
        }
    }

    public ArrayList<Double> getEncoderValues(){
        ArrayList<Double> encValues = new ArrayList<Double>();
        for (CANEncoder enc : sparkEncArray){
            encValues.add(enc.getPosition());
        }
        return encValues;
    }

    public void putEncodersToDash(){
        ArrayList<Double> encValues = getEncoderValues();
        for(int i = 0; i < encValues.size(); i++){
            SmartDashboard.putNumber("Motor Index" + i, encValues.get(i));
        }
    }

    public double getEncoderAverage(boolean absolute, int index, int... indexes){
        double sum = 0;
        Integer[] m_indexes = new Integer[indexes.length + 1];
        m_indexes[0] = index;
        for(int i = 0; i < m_indexes.length; i++){
            m_indexes[i + 1] = indexes[i];
        }
        ArrayList<Double> encValues = getEncoderValues();
        for(int j : indexes){
            if(absolute){
                sum += Math.abs(encValues.get(j));
            } else {
                sum += encValues.get(j);
            }
        }
        return sum/(indexes.length+1);
        
    }

    public double getEncoderAverage(){
        return getEncoderAverage(true, 0, 1, 2, 3);
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



















//     public void setPeanutLeft(double speed) {
//         peanutFrontLeft.set(ControlMode.PercentOutput, speed);
//         peanutBackLeft.set(ControlMode.PercentOutput, speed);
//     }

//     public void setPeanutRight(double speed) {
//         peanutFrontRight.set(ControlMode.PercentOutput, -speed);
//         peanutBackRight.set(ControlMode.PercentOutput, -speed);
//     }

//     public void killPeanutMotors() {
//         setPeanutLeft(0);
//         setPeanutRight(0);
//     }

//     public double getAveragepeanutEncoders() {
//         double avg;
//         avg = (-peanutFrontRight.getSelectedSensorPosition()
//             + peanutBackLeft.getSelectedSensorPosition()
//             - peanutBackRight.getSelectedSensorPosition()) / 3;
//         return avg;
//     }

//     public double getPeanutDistanceIn() {
//         double distance;
//         distance = getAveragepeanutEncoders() * (8.25 * Math.PI / 1300);
//         return distance;
//     }

//     public void resetPeanutEnoders() {
//         peanutFrontRight.getSensorCollection().setQuadraturePosition(0, 0);
// 		peanutFrontLeft.getSensorCollection().setQuadraturePosition(0, 0);
// 		peanutBackRight.getSensorCollection().setQuadraturePosition(0, 0);
// 		peanutBackLeft.getSensorCollection().setQuadraturePosition(0, 0);
//     }

//     public void printPeanutEncoders() {
//         System.out.println("peanutFrontLeft: " + (-peanutFrontLeft.getSelectedSensorPosition()));
//         System.out.println("peanutBackLeft: " + peanutBackLeft.getSelectedSensorPosition());
//         System.out.println("peanutFrontRight: " + (-peanutFrontRight.getSelectedSensorPosition()));
//         System.out.println("peanutBackRight: " + (-peanutBackRight.getSelectedSensorPosition()));
//     }

// }