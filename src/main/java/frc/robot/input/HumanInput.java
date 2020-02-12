package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;
//import frc.robot.controlpanel.BackupPlan;


public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button setColorRed, setColorGreen, setColorBlue, setColorYellow, BackupPlanButton;

  Button spinXTimes;
  String colorPressed = "";


  public HumanInput() {
    setColorRed = new JoystickButton(xbox, 1);
    setColorGreen = new JoystickButton(xbox, 2);
    setColorBlue = new JoystickButton(xbox, 3);
    setColorYellow = new JoystickButton(xbox, 4);
    BackupPlanButton = new JoystickButton(xbox, 7);
    spinXTimes = new JoystickButton(xbox, 8); // this is the "start" button
  }

  public void registerButtons() {
    setColorRed.whenPressed(new SpinToColorCommand("Yellow"));// was Green
    setColorGreen.whenPressed(new SpinToColorCommand("Blue"));
    setColorBlue.whenPressed(new SpinToColorCommand("Red"));
    setColorYellow.whenPressed(new SpinToColorCommand("Green"));

    spinXTimes.whenPressed(new SpinXTimesCommand()); 

   // BackupPlanButton.whenPressed(new BackupPlan());

  }
}
