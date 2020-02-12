/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

import javax.swing.colorchooser.ColorSelectionModel;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SpinToColorCommand extends CommandBase {

  /**
   * Creates a new SpinToColor.
   */

   private String targetColor;
  public SpinToColorCommand(String targetColor) {

    this.targetColor = targetColor;

    addRequirements(Robot.controlsubsys);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    Robot.controlsubsys.setTargetColor(targetColor); // put in intitalize and var passed locally because it doesn"t work in constructor without it 
    Robot.controlsubsys.startMotors();
    System.out.println("Spin to color is working in init.");

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
    System.out.println(Robot.controlsubsys.spinToColor());
    return Robot.controlsubsys.spinToColor();

  }
}
