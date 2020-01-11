/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sample;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;

public class SampleServoSubsystem extends SubsystemBase {
  private Servo servo;

  private static SampleServoSubsystem instance = null;

  private SampleServoSubsystem() {
      servo = new Servo(Ports.SERVO_PORT);
  }

  // Singleton design pattern
  public static SampleServoSubsystem getInstance() {
      if (instance == null) {
          instance = new SampleServoSubsystem();
      }
      return instance;
  }

  public void setAngle(double degrees) {
    servo.setAngle(degrees);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
