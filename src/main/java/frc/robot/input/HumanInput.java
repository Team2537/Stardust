package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.nav.DriveStraightCommand;
import frc.robot.nav.RealignmentCommand;
import frc.robot.nav.RotateCommand;
//import frc.robot.nav.TestCommand;
import frc.robot.nav.TestPath;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;
  private static JoystickButton realignButton, rotate90BUtton, rotateneg90Button, driveStraightButton, testPathButton/*, testCom*/;

  public HumanInput() {
      joystickLeft = new Joystick(0);
      joystickRight = new Joystick(1);
      // xbox = new XboxController(2);
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
    // realignButton.whenPressed(new RealignmentCommand());
    // rotate90BUtton.whenPressed(new RotateCommand(90));
    // rotateneg90Button.whenPressed(new RotateCommand(-90));
    // driveStraightButton.whenPressed(new DriveStraightCommand(60.0));
    // testPathButton.whenPressed(new TestPath());
    //testCom.whenPressed(new TestCommand());
  }
}