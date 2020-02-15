package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;

public class HumanInput {
  XboxController xbox = new XboxController(0);

  Button presetPosition = new JoystickButton(xbox, Ports.PRESET_POSITION_BUTTON);
  Button winchButton = new JoystickButton(xbox, Ports.WINCH_BUTTON);


  public HumanInput() {
      //This is for the left trigger -- in order to make it act like a button, the command 
      //runs after the trigger goes past halfway
      if(getLeftTrigger() > .5) {
        winchButton.whenHeld(new WinchCommand());
      }

      //Runs once joystick is moved (set to above .1 in order to prevent accidental movement)
      if(getJoystickAxis() > .1) {
        new MoveTelescopeCommand();
      }
      
      //Button for setting telescope to preset height
      presetPosition.whenPressed(new PresetPositionCommand());
  }

  public double getJoystickAxis() {
    return xbox.getY(Hand.kRight);
  }

  public double getLeftTrigger() {
    return xbox.getTriggerAxis(Hand.kLeft);
  }
}