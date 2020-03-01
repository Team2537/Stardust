/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;

public class ClimbCommand extends CommandBase {
  
  public ClimbCommand() {    
    addRequirements(Robot.climbsys);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //Checks if telescope is fully extended; if not, runs telescope
    if(!Robot.climbsys.isFullyExtended()) { //this makes 
      Robot.climbsys.setTelescopeSpeed(Robot.humanInput.getXboxRightJoystickAxis() /(.75));
    } 
    else {
      Robot.climbsys.setTelescopeSpeed(0);
    }

    //Winch isn't special, it doesn't get an if statement. It just does the thing no matter where the bot is.
    Robot.climbsys.setWinchSpeed(Robot.humanInput.getXboxLeftTrigger());
  }

  @Override
  public void end(boolean interrupted) {
    //Runs when enable climb is released 
    Robot.climbsys.setTelescopeSpeed(0);
    Robot.climbsys.setWinchSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //It never finishes. It is doomed to run for eternity. 
    return false;
  }
}
