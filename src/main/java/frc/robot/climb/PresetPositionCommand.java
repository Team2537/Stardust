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
  
  public PresetPositionCommand() {
    addRequirements(Robot.climbsys);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //Do thing!
    Robot.climbsys.setTelescopeSpeed(.1);
  }

  @Override
  public void end(boolean interrupted) {
    //This is what actually stops the motors once it realizes it's near the sensors, i.e. very important shit
    Robot.climbsys.setTelescopeSpeed(0);
  }

  @Override
  public boolean isFinished() {
   return Robot.climbsys.isFullyExtended();
  }
}
