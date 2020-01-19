/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Counter;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  private static ShooterSubsystem instance = null;
  public static TalonSRX shooterMotor;
  private DigitalOutput lidarMode;
  private Counter lidarDistance;
  public static CANSparkMax ShooterMotor;
  public static CANEncoder ShooterEncoder;
  public static CANPIDController ShooterVelocityController;
  private static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;`



  private ShooterSubsystem() {

    //Connects Lidar mode control to DIO port 5
    lidarMode = new DigitalOutput(Ports.LIDAR_MODE);
    lidarMode.set(false); //Lidar mode is active low
    
    //Connects semi period mode to DIO port 4
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

  /*private static double lidarValue() {

  }*/
  public double getLidarDistance() {
    /* The lidar gives you a pulse that tells you the distance between the object and itself.
    It reads out as 10 micro seconds per centimeter so we have to multiply it by 100000 to make
    it read as centimeters. */
   
    double in = (lidarDistance.getPeriod() * 1000000.0/25.4);
      return in;

    }

  //}

  public static void startMotor(double MotorVelocity) {

    ShooterVelocityController.setReference(MotorVelocity, ControlType.kVelocity);

  }



}
