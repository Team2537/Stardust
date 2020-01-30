/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.input.Ports;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Counter;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  private static ShooterSubsystem instance = null;
  private DigitalOutput lidarMode;
  private static Counter lidarDistance;
  private static CANSparkMax ShooterMotor;
  private static CANPIDController ShooterVelocityController;
  private static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  private static boolean runShooter;
  private static double MAX_SPEED;
  private static CANSparkMax feederMotor;

  private ShooterSubsystem() {

    // Connects Lidar mode control to DIO port 5
    lidarMode = new DigitalOutput(Ports.LIDAR_MODE);
    lidarMode.set(false); // Lidar mode is active low

    // Connects semi period mode to DIO port 4
    lidarDistance = new Counter(Counter.Mode.kSemiperiod);
    lidarDistance.setUpSource(Ports.LIDAR_COUNTER);
    lidarDistance.setSemiPeriodMode(true);

    kP = 5e-5;
    kI = 1e-6;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;
    runShooter = false;
    MAX_SPEED = 1250;

    feederMotor = new CANSparkMax(Ports.FEEDER_MOTOR_PORT, MotorType.kBrushless);
    ShooterMotor = new CANSparkMax(Ports.MOTOR_SHOOTER_PORT, MotorType.kBrushless);

    ShooterVelocityController = ShooterMotor.getPIDController();
    ShooterVelocityController.setP(kP);
    ShooterVelocityController.setI(kI);
    ShooterVelocityController.setD(kD);
    ShooterVelocityController.setIZone(kIz);
    ShooterVelocityController.setFF(kFF);
    ShooterVelocityController.setOutputRange(kMinOutput, kMaxOutput);
  }

  public static ShooterSubsystem getInstance() {
    if (instance == null) {
      instance = new ShooterSubsystem();
    }
    return instance;
  }

  /*
   * private static double lidarValue() {
   * 
   * }
   */

  public static void setShooterMode(boolean value) {
    runShooter = value;
  }

  public static boolean getShooterMode() {
    return runShooter;
  }

  public static double getLidarDistance() {
    /*
     * The lidar gives you a pulse that tells you the distance between the object
     * and itself. It reads out as 10 micro seconds per centimeter so we have to do
     * stuff to convert it to make it read as inches.
     */

    double in = (lidarDistance.getPeriod() * 1000000.0 / 25.4);
      
      return in;

  }

  public static void automaticallySetProperSpeed(double in) {

    double setpoint, theta, yNot, y;
    theta = 45;
    yNot = 31.496;
    y = 98.425;
    setpoint = Math.sqrt((-192.91*Math.pow(in, 2))/(Math.pow(Math.cos(Math.toRadians(theta)), 2)*(y-yNot-(Math.tan(Math.toRadians(theta))*in)))); //converts our lidar distance to a setpoint for the motor using kinematics
    setpoint = (setpoint*10)/Math.PI; //converts inches per second to rotations per minute
    setpoint = Math.min(setpoint, MAX_SPEED);
    startMotor(setpoint);

  }

  //}

  public static void startMotor(double MotorVelocity) {

    ShooterVelocityController.setReference(MotorVelocity, ControlType.kVelocity);

  }

  public static void startFeederMotor() {

    feederMotor.set(0.2);

  }

}
