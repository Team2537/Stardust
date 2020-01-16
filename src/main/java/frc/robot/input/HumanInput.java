package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.nav.RealignmentCommand;
import frc.robot.nav.RotateCommand;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;
  public final XboxController xbox;
  private static JoystickButton newButton;

  public HumanInput() {
      joystickLeft = new Joystick(0);
      joystickRight = new Joystick(1);
      xbox = new XboxController(2);
      newButton = new JoystickButton(joystickLeft, 0);
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

  public void registerButtons(){
    newButton.whenPressed(new RealignmentCommand());
  }
}