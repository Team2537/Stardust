/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.input.Ports;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.util.CANTalon;


public class ClimbSubsystem extends SubsystemBase {
  /**
   * Creates a new ClimbSubsystem.
   */

  private static ClimbSubsystem instance = null;

  private CANSparkMax climbCANWinch;
  private CANTalon climbCANTelescope;
  private DigitalInput climbDITelescope;

  private ClimbSubsystem() {
    climbCANWinch = new CANSparkMax(Ports.CLIMB_WINCH, MotorType.kBrushless);
    climbCANTelescope = new CANTalon(Ports.CLIMB_TELESCOPE);
    climbDITelescope = new DigitalInput(Ports.TOP_SENSOR);
  }

  public static ClimbSubsystem getInstance() {
    if (instance == null) {
        instance = new ClimbSubsystem();
    }
    return instance;
  }

  //Run Motors
  public void setWinchSpeed (double speed) {
    climbCANWinch.set(speed);
  }

  public void setTelescopeSpeed(double speed){
    climbCANTelescope.set(speed);
  }

  //Get t/f values from Hall Effect Sensor on Telescope
  public boolean getClimbDITelescope() {
    return climbDITelescope.get();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
