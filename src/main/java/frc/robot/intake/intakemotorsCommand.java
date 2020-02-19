/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

//import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
public class IntakeMotorsCommand extends CommandBase {

  /**
   * Creates a new lowerIntake.
   */
  public IntakeMotorsCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.intakesys);
    

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    //Robot.intakesys.setSolenoid(!Robot.intakesys.getSolenoid());
    Robot.intakesys.setSolenoid(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   // Robot.intakesys.setSpeed(.4);
   System.out.println("SET SOLENOID to " + Robot.intakesys.getSolenoid());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //Robot.intakesys.setSpeed(0);
    Robot.intakesys.setSolenoid(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
