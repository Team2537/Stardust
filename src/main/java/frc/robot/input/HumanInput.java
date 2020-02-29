package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drive.SwitchDriveCommand;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;

    XboxController xbox;
    Button tankButton;

  public HumanInput() {
      joystickLeft = new Joystick(0);
      joystickRight = new Joystick(1);
      xbox = new XboxController(Ports.XBOX_CONTROLLER);
      tankButton = new JoystickButton(joystickLeft, Ports.TANK_BUTTON);
  }

  public double getJoystickAxis(int axis, GenericHID joystick, double deadzone){
    double val = joystick.getRawAxis(axis);
    if (Math.abs(val) <= deadzone){
      return 0;
    } else {
      return val;
    }
  }

  public double getJoystickAxisLeft(int axis){
    return getJoystickAxis(axis, joystickLeft, DEADZONE);
  }

  public double getJoystickAxisRight(int axis){
    return getJoystickAxis(axis, joystickRight, DEADZONE);
  }
  public void getRegister(){
  //tankButton.whenPressed(new SwitchDriveCommand());
  }

public void registerButtons() {
}

public double getXboxRightTrigger() {
	return 0;
}

public double getXboxLeftTrigger() {
	return 0;
}

public int getXboxRightJoystickAxis() {
	return 0;
}
}