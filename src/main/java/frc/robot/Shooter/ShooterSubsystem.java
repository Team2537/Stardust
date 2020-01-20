/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.input.Ports;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ShooterSubsystem {
  /**
   * Creates a new ShooterSubsystem.
   */
  private static ShooterSubsystem instance = null;
  public static CANSparkMax ShooterMotor;
  public static CANEncoder ShooterEncoder;
  public static CANPIDController ShooterVelocityController;
  private static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;


  private ShooterSubsystem() {

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

  /*private static double lidarValue() {

  }*/

  public static void startMotor(double MotorVelocity) {

    ShooterVelocityController.setReference(MotorVelocity, ControlType.kVelocity);

  }


  public static ShooterSubsystem getInstance() {
    if (instance == null) {
        instance = new ShooterSubsystem();
    }
    return instance;
  }
}
