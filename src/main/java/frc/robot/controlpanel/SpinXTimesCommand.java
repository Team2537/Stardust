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

public class SpinXTimesCommand extends CommandBase {
  /**
   * Creates a new SpinXTimes.
   */
  public SpinXTimesCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.controlsubsys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.controlsubsys.zeroCounters(); // sets the R,G,B,Y, and revolutions value to 0
    Robot.controlsubsys.startShooterMotors(Ports.X_ROTATIONS_POWER);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.controlsubsys.stopShooterMotors();
  }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.controlsubsys.isWheelSpun4Times();
  }
}