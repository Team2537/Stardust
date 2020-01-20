package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button openButton = new JoystickButton(xbox, Ports.OPEN_BUTTON);
  Button closeButton = new JoystickButton(xbox, Ports.CLOSE_BUTTON);

  public HumanInput() {

  }
}