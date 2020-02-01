/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;
import frc.robot.util.CANTalon;

public class climbSubsystem extends SubsystemBase {
  /**
   * Creates a new climbSubsystem.
   */

  private static climbSubsystem instance = null;

  private CANSparkMax Neo1;
  private CANSparkMax Neo2;
  private CANTalon miniCim; 
  private DigitalInput upLimit;
  private DigitalInput downLimit;
  private CANEncoder Neo1Enc;
  private CANEncoder Neo2Enc;

  public boolean upDown = true;

  private climbSubsystem() {
    Neo1 = new CANSparkMax(Ports.Neo1ID, MotorType.kBrushless);
    Neo2 = new CANSparkMax(Ports.Neo2ID, MotorType.kBrushless);
    miniCim = new CANTalon(Ports.MiniCimID);
    upLimit = new DigitalInput(Ports.UpLimitID);
    downLimit = new DigitalInput(Ports.DownLimitID);
    Neo1Enc = new CANEncoder(Neo1, EncoderType.kQuadrature, 720);
    Neo2Enc = new CANEncoder(Neo2, EncoderType.kQuadrature, 720);
  }

  public static climbSubsystem getInstance() {
    if (instance == null) {
        instance = new climbSubsystem();
    }
    return instance;
  }

  public void runNeo1 (double speed) {
    Neo1Enc.setPosition(3);
    //units are rotations
  }

  public void runNeo2 (double speed) {
    Neo2Enc.setPosition(3);
  }

  public boolean reachedTop() {
    return upLimit.get();
  }

  public boolean reachedBottom() {
    return downLimit.get();
  }

  public void runMiniCim (double speed) {
    miniCim.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
