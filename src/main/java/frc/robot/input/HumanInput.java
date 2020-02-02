package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button setColorRed, setColorGreen, setColorBlue, setColorYellow;

  Button spinXTimes;
  
  public HumanInput() {
    setColorRed = new JoystickButton(xbox, 1);
    setColorGreen = new JoystickButton(xbox, 2);
    setColorBlue = new JoystickButton(xbox, 3);
    setColorYellow = new JoystickButton(xbox, 4);

    spinXTimes = new JoystickButton(xbox, 8); // this is the "start" button
  }

  public void registerButtons() {
    setColorRed.whenPressed(new SpinToColorCommand("Green"));
    setColorGreen.whenPressed(new SpinToColorCommand("Red"));
    setColorBlue.whenPressed(new SpinToColorCommand("Blue"));
    setColorYellow.whenPressed(new SpinToColorCommand("Yellow"));

    spinXTimes.whenPressed(new SpinXTimesCommand()); 
  }
}
