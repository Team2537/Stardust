package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;
import frc.robot.sample.*;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button upButton = new JoystickButton(xbox, Ports.UP_BUTTON);
  Button downButton = new JoystickButton(xbox, Ports.DOWN_BUTTON);
  Button position1 = new JoystickButton(xbox, Ports.POSITION1_BUTTON);
  Button raiseButton = new JoystickButton(xbox, Ports.RAISE_BUTTON);
  
  public boolean upDown = true;

  public HumanInput() {
      upButton.whenHeld(new telescopeUpCommand());
      downButton.whenHeld(new telescopeDownCommand());
      raiseButton.whenHeld(new raiseRobotCommand());
  }
}