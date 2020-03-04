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


public class ClimbSubsystem extends SubsystemBase {

  private static ClimbSubsystem instance = null;

  private CANSparkMax climbCANWinch;
  private CANTalon climbCANTelescope;
  private DigitalInput climbDITelescope;

  private ClimbSubsystem() {
    climbCANWinch = new CANSparkMax(Ports.CLIMB_WINCH, MotorType.kBrushless);
    climbCANTelescope = new CANTalon(Ports.CLIMB_TELESCOPE);
    climbDITelescope = new DigitalInput(Ports.TOP_SENSOR);
    climbCANTelescope.configFactoryDefault();
  }

  public static ClimbSubsystem getInstance() {
    if (instance == null) {
        instance = new ClimbSubsystem();
    }
    return instance;
  }

  //ya. yeet. 
  public void setWinchSpeed (double speed) {
    climbCANWinch.set(speed);
  }
  
  //ya yeet 2: the yeetening
  public void setTelescopeSpeed(double speed){
    climbCANTelescope.set(speed);
  }

  //Magnet or no magnet? That is the question. 
  public boolean isFullyExtended() {
    //Hall Effect Sensor returns false when magnet is nearby
    return !climbDITelescope.get();
  }


  @Override
  public void periodic() {
  }
}
