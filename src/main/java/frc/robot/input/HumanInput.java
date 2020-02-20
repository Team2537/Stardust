package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;

public class HumanInput {
  XboxController xbox = new XboxController(0);

  Button presetPosition = new JoystickButton(xbox, Ports.PRESET_POSITION_BUTTON);
  Button enableClimb = new JoystickButton(xbox, Ports.ENABLE_CLIMB_BUTTON);
  
  public final double DEADZONE = .1;

  public HumanInput() {
    //Button to allow winch and telescope to be run
    //(This is the thing I tried to use the command scheduler for and failed miserably so this was the solution :/)
    enableClimb.whenHeld(new ClimbCommand());

    //Button for setting telescope to preset height (it goes schwoop)
    presetPosition.whenPressed(new PresetPositionCommand());
  }

  public double getJoystickAxis() {
    double val = xbox.getY(Hand.kRight);

    //In order to make sure someone's filthy green sausages don't accidentally hit the joystick,
    //it only runs when pushed passed a certain point
    if(Math.abs(val) > DEADZONE) {
      return val;
    }
    else {
      return 0;
    }
  }

  public double getLeftTrigger() {
    double val = xbox.getTriggerAxis(Hand.kLeft);

    //Trigger operates like a button, if it's value is below .5, it doesn't run at all, 
    //if it is above, it only returns a previously set speed. This is to make sure Kinsley doesn't 
    //get all trigger happy on us and make the bot go zoom zoom
    if(val > .5) {
      return -.5;
    }
    else {
      return 0;
    }
  }
}