/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class telescopeDownCommand extends CommandBase {
  /**
   * Creates a new elevatorDown.
   */
  public telescopeDownCommand() {
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
      Robot.climbsys.runTeleMtr(-.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.climbsys.runTeleMtr(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Robot.climbsys.getBottomSensor()) {
      System.out.println("Not true yet");
      return false;
    }
    else {
      System.out.println("True, should be stopping");
      return true;
    }
  }
}
