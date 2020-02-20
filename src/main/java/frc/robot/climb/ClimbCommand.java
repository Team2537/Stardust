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
  /**
   * Creates a new ClimbCommand.
   */
  public ClimbCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //The Hall Effect Sensors return false when a magnet is nearby (cause that makes sense) so 
    //when the sensor returns true, the motor should continue running. If it is false, that's when 
    //we abort mission before the telescope turns into a projectile
    if(Robot.climbsys.getClimbDITelescope()) {
      Robot.climbsys.setTelescopeSpeed(Robot.humanInput.getJoystickAxis() / 3);
    }
    else {
      Robot.climbsys.setTelescopeSpeed(0);
    }

    //Winch isn't special, it doesn't get an if statement. It just does the thing no matter where the bot is.
    Robot.climbsys.setWinchSpeed(Robot.humanInput.getLeftTrigger());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //I'm not entirely sure why the command would be interrupted but just in case all hell breaks loose, 
    //it kills the motors which is always a good idea. 
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
