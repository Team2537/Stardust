/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climbSubsystem extends SubsystemBase {
  /**
   * Creates a new climbSubsystem.
   */

  private static climbSubsystem instance = null;

  private Servo pivotMotor = new Servo(1);
  private CANSparkMax Neo1 = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax Neo2 = new CANSparkMax(2, MotorType.kBrushless);
  private DigitalInput upLimit = new DigitalInput(1);
  private DigitalInput downLimit = new DigitalInput(1);

  public boolean upDown = true;

  private climbSubsystem() {

  }

  public static climbSubsystem getInstance() {
    if (instance == null) {
        instance = new climbSubsystem();
    }
    return instance;
  }

  public void pivot () {
    if(upDown) {
      pivotMotor.setAngle(90);
      upDown = false;
    }
    else {
      pivotMotor.setAngle(0);
      upDown = true;
    }
  }

  public void runNeo1 (double speed) {
    Neo1.set(speed);
  }

  public void runNeo2 (double speed) {
    Neo2.set(speed);
  }

  public boolean reachedTop() {
    return upLimit.get();
  }

  public boolean reachedBottom() {
    return downLimit.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}