/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TelescopeResetCommand extends CommandBase {
  /**
   * Creates a new TelescopeResetCommand.....
   */
  public TelescopeResetCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.climbsys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //Set negative to descend
      Robot.climbsys.runClimbCANTelescope(-.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.climbsys.runClimbCANTelescope(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //The Hall Effect Sensors return false when a magnet is nearby so when .getCLimbDIBottom is true, 
    //the code should continue running. If it is false, that's when the telescope should stop.
    if(Robot.climbsys.getClimbDIBottom()) {
      return false;
    }
    else {
      return true;
    }
  }
}