/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Shooter.ShooterSubsystem;

public class LoadBallCommand extends CommandBase {
  /**
   * Creates a new BalLocationCommand.
   */

  public LoadBallCommand() {
    addRequirements(Robot.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ShooterSubsystem.startFeederMotor(-0.7);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ShooterSubsystem.startFeederMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ShooterSubsystem.ballInPlace();
  }
}
