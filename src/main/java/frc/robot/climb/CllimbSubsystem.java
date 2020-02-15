/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.util.CANTalon;


public class CllimbSubsystem extends SubsystemBase {
  /**
   * Creates a new ClimbSubsystem.
   */

  private static CllimbSubsystem instance = null;

  private CANSparkMax climbCANWinch;
  private CANTalon climbCANTelescope;
  private DigitalInput climbDITop, climbDIBottom;

  private CllimbSubsystem() {
    climbCANWinch = new CANSparkMax(Ports.CLIMB_WINCH, MotorType.kBrushless);
    climbCANTelescope = new CANTalon(Ports.CLIMB_TELESCOPE);
    climbDITop = new DigitalInput(Ports.TOP_SENSOR);
    climbDIBottom = new DigitalInput(Ports.BOTTOM_SENSOR);
  }

  public static CllimbSubsystem getInstance() {
    if (instance == null) {
        instance = new CllimbSubsystem();
    }
    return instance;
  }

  //Run Motors
  public void runClimbCANWinch (double speed) {
    climbCANWinch.set(speed);
  }

  public void runClimbCANTelescope (double speed) {
    climbCANTelescope.set(speed);
  }

  //Get t/f values from Hall Effect Sensors on Telescope (on the top and bottom)
  public boolean getClimbDITop() {
    return climbDITop.get();
  }

  public boolean getClimbDIBottom() {
    return climbDIBottom.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
