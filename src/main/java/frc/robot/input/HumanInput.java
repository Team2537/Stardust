package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;

public class HumanInput {
  XboxController xbox = new XboxController(0);

  Button presetPosition = new JoystickButton(xbox, Ports.PRESET_POSITION_BUTTON);
  
  public final double DEADZONE = .1;

  public HumanInput() {
      //Button for setting telescope to preset height
      presetPosition.whenPressed(new PresetPositionCommand());
  }

  public double getJoystickAxis() {
    double val = xbox.getY(Hand.kRight);

    if(Math.abs(val) > DEADZONE) {
      return val;
    }
    else {
      return 0;
    }
  }

  public double getLeftTrigger() {
    double val = xbox.getTriggerAxis(Hand.kLeft);

    if(val > .5) {
      return .2;
    }
    else {
      return 0;
    }
  }
}