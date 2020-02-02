/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class SpinXTimesCommand extends CommandBase {
  /**
   * Creates a new SpinXTimes.
   */
  public SpinXTimesCommand() {
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
Robot.controlsubsys.startMotors();

Robot.controlsubsys.ZeroRevolutions();
Robot.controlsubsys.ZeroNumBlue();
Robot.controlsubsys.ZeroNumGreen();
Robot.controlsubsys.ZeroNumYellow();
Robot.controlsubsys.ZeroNumRed();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   // System.out.println("Spin X times is working in execute.");
   Robot.controlsubsys.detectCorrectColor(Robot.controlsubsys.detectColor());
    if (Robot.controlsubsys.NumRed() >= Robot.controlsubsys.getRevolutions() + 1 && Robot.controlsubsys.NumGreen() >= Robot.controlsubsys.getRevolutions() + 1 && Robot.controlsubsys.NumBlue() >= Robot.controlsubsys.getRevolutions() + 1
    && Robot.controlsubsys.NumYellow() >= Robot.controlsubsys.getRevolutions() + 1) {
      Robot.controlsubsys.setRevolutions();
}
if (Robot.controlsubsys.getRevolutions() == 8) {
  Robot.controlsubsys.stopMotors();
}

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
     //reset reveolutions and numred,green,blue,yellow
    Robot.controlsubsys.ZeroRevolutions();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
