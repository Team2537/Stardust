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

public class ClimbSubsystem extends SubsystemBase {
  /**
   * Creates a new ClimbSubsystem.
   */

  private static ClimbSubsystem instance = null;

  private CANSparkMax Neo1;
  private CANSparkMax Neo2;
  private CANTalon miniCim;

  private ClimbSubsystem() {
    Neo1 = new CANSparkMax(Ports.NEO_1, MotorType.kBrushless);
    Neo2 = new CANSparkMax(Ports.NEO_2, MotorType.kBrushless);
    miniCim = new CANTalon(Ports.MINICIM);
  }

  public static ClimbSubsystem getInstance() {
    if (instance == null) {
        instance = new ClimbSubsystem();
    }
    return instance;
  }

  public void runNeo1 (double speed) {
    Neo1.set(speed);
  }

  public void runNeo2 (double speed) {
    Neo2.set(speed);
  }

  public void runMiniCim (double speed) {
    miniCim.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}