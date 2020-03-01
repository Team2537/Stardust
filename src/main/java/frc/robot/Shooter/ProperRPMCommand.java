/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.input.Ports;

public class ProperRPMCommand extends CommandBase {
  private double waitTime = 100;
  private final int TOLERANCE = 100;
  private long currentTime;
  /**
   * Creates a new ProperRPMCommand.
   */
  public ProperRPMCommand() {
    addRequirements(Robot.shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentTime = System.currentTimeMillis();
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
    return (Robot.shooter.TARGET_SPEED - TOLERANCE <= ShooterSubsystem.getInstance().getShooterSpeed()  
            && ShooterSubsystem.getInstance().getShooterSpeed() <= Robot.shooter.TARGET_SPEED + TOLERANCE
            && System.currentTimeMillis() - currentTime >= waitTime);
  }
}
