package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.intake.MoveIntakeCommand;
import frc.robot.input.Ports;
public class HumanInput {
  XboxController xbox;
  Button intakeButton, intakeButton2, flywheelTest, reverse;

  public HumanInput() {
   
    xbox = new XboxController(Ports.XBOXCONTROLLER);
    intakeButton = new JoystickButton(xbox,Ports.INTAKEBUTTON); 


  }
  public void registerButtons(){
  intakeButton.whenPressed(new MoveIntakeCommand()); //Y toggle between true and false
  
  


  }
}