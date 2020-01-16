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

public class ShooterSubsystem {
  /**
   * Creates a new ShooterSubsystem.
   */
  private static ShooterSubsystem instance = null;
  public static TalonSRX ShooterMotor;

  public ShooterSubsystem() {

    ShooterMotor = new TalonSRX(Ports.MOTOR_SHOOTER_PORT);

  }

  /*private static double lidarValue() {

  }*/

  public static void startMotor(double MotorVelocity) {

    ShooterMotor.set(ControlMode.Velocity, MotorVelocity);

  }


  public static ShooterSubsystem getInstance() {
    if (instance == null) {
        instance = new ShooterSubsystem();
    }
    return instance;
  }
}
