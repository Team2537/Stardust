/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TestCommmand extends CommandBase {
  String output;
  /**
   * Creates a new TestCommmand.
   */
  public TestCommmand(String output) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.output = output;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println(output);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
