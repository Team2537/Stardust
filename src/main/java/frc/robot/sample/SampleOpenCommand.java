/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sample;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SampleOpenCommand extends CommandBase {
  /**
   * Creates a new SampleOpenCommand.
   */
  public SampleOpenCommand() {
    addRequirements(Robot.servosys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("Open Pressed.");
    Robot.servosys.setAngle(30); // set servo to open position
    return true;                // and then finish
  }
}
