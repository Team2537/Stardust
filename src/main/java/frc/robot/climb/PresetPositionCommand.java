/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class PresetPositionCommand extends CommandBase {
  /**
   * Creates a new PresetPositionCommand.
   */
  public PresetPositionCommand() {
    addRequirements(Robot.climbsys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Do thing!
    Robot.climbsys.setTelescopeSpeed(.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //This is what actually stops the motors once it realizes it's near the sensors, i.e. very important shit
    Robot.climbsys.setTelescopeSpeed(0);
    System.out.println("ENDED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //The Hall Effect Sensors return false when a magnet is nearby (cause that makes sense) so 
    //when the sensor returns true, the motor should continue running. If it is false, that's when 
    //we abort mission before the telescope turns into a projectile
   if(Robot.climbsys.getClimbDITelescope()) {
    System.out.println("FALSE");
     return false;
   }
   else {
    System.out.println("TRUE");
     return true;
   }
  }
}
