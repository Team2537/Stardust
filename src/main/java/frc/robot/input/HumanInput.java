package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.intake.intakeSubsystem;
import frc.robot.intake.lowerIntakeCommand;
import frc.robot.sample.SampleCloseCommand;
import frc.robot.sample.SampleOpenCommand;
import frc.robot.intake.intakeSubsystem;
import frc.robot.intake.lowerIntakeCommand;
import frc.robot.intake.raiseIntakeCommand;
import frc.robot.intake.intakemotorsCommand;
import frc.robot.input.Ports;
public class HumanInput {
  XboxController xbox;
 // Button openButton = new JoystickButton(xbox, Ports.OPEN_BUTTON);
 // Button closeButton = new JoystickButton(xbox, Ports.CLOSE_BUTTON);
  Button intakeButton, intakeButton2, flywheelTest, reverse;

  public HumanInput() {
    //openButton.whenPressed(new SampleOpenCommand());
   // closeButton.whenPressed(new SampleCloseCommand());
   
xbox = new XboxController(Ports.XBOXCONTROLLER);
intakeButton = new JoystickButton(xbox,Ports.INTAKEBUTTON); 
intakeButton2 = new JoystickButton(xbox,Ports.INTAKEBUTTON2); 
flywheelTest = new JoystickButton(xbox, 3);
reverse = new JoystickButton(xbox, 4);

  }
  public void registerButtons(){
  intakeButton.whenPressed(new lowerIntakeCommand()); //intake A
  intakeButton2.whenPressed(new raiseIntakeCommand()); //intake B
   //flywheelTest.whenPressed(new intakemotorsCommand());

  }
}