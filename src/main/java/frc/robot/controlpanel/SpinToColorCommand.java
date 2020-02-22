/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.input.Ports;
import frc.robot.Robot;

public class SpinToColorCommand extends CommandBase {
  /**
   * Creates a new SpinToColor.
   */

  public SpinToColorCommand() {
    addRequirements(Robot.controlsubsys);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (Robot.controlsubsys.isOnTargetColor()){
      Robot.controlsubsys.stopMotors(); 
    } 
    else {
     Robot.controlsubsys.startMotors(Ports.SPIN_TO_COLOR_POWER);
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.controlsubsys.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Robot.controlsubsys.isOnTargetColor()){
      Robot.controlsubsys.stopMotors();
      return true;
    } 
    return false;

  }

}