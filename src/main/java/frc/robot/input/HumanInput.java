package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;

public class HumanInput {
  XboxController xbox = new XboxController(0);

  Button manualUpButton = new JoystickButton(xbox, Ports.MANUAL_UP_BUTTON);
  Button resetTelescope = new JoystickButton(xbox, Ports.RESET_BUTTON);
  Button presetPosition = new JoystickButton(xbox, Ports.PRESET_POSITION_BUTTON);
  Button winchButton = new JoystickButton(xbox, Ports.WINCH_BUTTON);

  public HumanInput() {
      //These run the motors for the duration of the time the button is held
      manualUpButton.whenHeld(new TelescopeManualUpCommand());
      winchButton.whenHeld(new WinchCommand());

      //These are automated commands, once the button is pressed they set the telescope 
      //to the preset position and the bottom respectively
      presetPosition.whenPressed(new PresetPositionCommand());
      resetTelescope.whenPressed(new TelescopeResetCommand());
      
  }
}