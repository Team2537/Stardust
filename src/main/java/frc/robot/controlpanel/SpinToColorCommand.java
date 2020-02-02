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
  private String targetColor;
  private boolean done;
  private String detectedColor;

  /**
   * Creates a new SpinToColor.
   */
  public SpinToColorCommand(String targetColor) {
    this.targetColor = targetColor;

    addRequirements(Robot.controlsubsys);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    detectedColor = "";
    Robot.controlsubsys.startMotors();
    System.out.println("Spin to color is working in init.");
    done = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

System.out.println(done);


System.out.println("Spin to color is working in execute.");




    detectedColor = Robot.controlsubsys.detectColor();
    if (Robot.controlsubsys.getColorString().equals("Red") && (Robot.controlsubsys.getLastColor().equals("Yellow") || Robot.controlsubsys.getLastColor().equals("Green") || Robot.controlsubsys.getLastColor().equals(""))) {
      Robot.controlsubsys.setLastColor("Red");
      if (detectedColor.equals(targetColor)){
        done = true;
      }

    } else if (Robot.controlsubsys.getColorString().equals("Green")
        && (Robot.controlsubsys.getLastColor().equals("Red") || Robot.controlsubsys.getLastColor().equals("Yellow") || Robot.controlsubsys.getLastColor().equals(""))) {
      /*
       * if (Robot.controlsubsys.getLastColor().equals("Yellow")) { numYellow--; }
       */
      Robot.controlsubsys.setLastColor("Green");
      if (targetColor == "Green"){
        done = true;
      }

    } else if (Robot.controlsubsys.getColorString().equals("Blue") && (Robot.controlsubsys.getLastColor().equals("Green") || Robot.controlsubsys.getLastColor().equals("Yellow") || Robot.controlsubsys.getLastColor().equals(""))) {
      Robot.controlsubsys.setLastColor("Blue");
      if (targetColor == "Blue"){
        done = true;
      }
    

    } else if (Robot.controlsubsys.getColorString().equals("Yellow")
        && (Robot.controlsubsys.getLastColor().equals("Blue") || Robot.controlsubsys.getLastColor().equals("Green") || Robot.controlsubsys.getLastColor().equals(""))) {
      /*
       * if (Robot.controlsubsys.getLastColor().equals("Green")) numGreen--;
       */
      Robot.controlsubsys.setLastColor("Yellow");
      if (targetColor == "Yellow"){
        done = true;
      }
    }
  
    }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.controlsubsys.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;

  }
}
