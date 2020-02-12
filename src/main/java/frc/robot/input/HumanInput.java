package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.intake.lowerIntakeCommand;

import frc.robot.intake.raiseIntakeCommand;

import frc.robot.input.Ports;
public class HumanInput {
  XboxController xbox;
 // Button openButton = new JoystickButton(xbox, Ports.OPEN_BUTTON);
 // Button closeButton = new JoystickButton(xbox, Ports.CLOSE_BUTTON);
  Button intakeButton;

  public HumanInput() {
    //openButton.whenPressed(new SampleOpenCommand());
   // closeButton.whenPressed(new SampleCloseCommand());
   
xbox = new XboxController(Ports.XBOXCONTROLLER);
intakeButton = new JoystickButton(xbox,Ports.INTAKEBUTTON); 

  

  }
  public void registerButtons(){
    intakeButton.whenPressed(new lowerIntakeCommand());
   intakeButton.whenPressed(new raiseIntakeCommand());
  }
}