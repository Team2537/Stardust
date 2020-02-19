package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drive.SwitchDriveCommand;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.intake.IntakeMotorsCommand;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;
    XboxController xbox;
    Button tankButton;
    Button intakeButton, intakeButton2, flywheelTest, reverse;

  public HumanInput() {
      joystickLeft = new Joystick(0);
      joystickRight = new Joystick(1);
      xbox = new XboxController(Ports.XBOX_CONTROLLER);
      tankButton = new JoystickButton(joystickLeft, Ports.TANKBUTTON);
      intakeButton = new JoystickButton(xbox, Ports.INTAKEBUTTON); 
  }

  public double getJoystickAxis(int axis, GenericHID joystick, double deadzone){
    double val = joystick.getRawAxis(axis);
    if (Math.abs(val) <= deadzone){
      return 0;
    } else {
      return val;
    }
  }

  public void getRegister(){
    intakeButton.toggleWhenPressed(new IntakeMotorsCommand()); //Y toggle between true and false
    tankButton.whenPressed(new SwitchDriveCommand());
  
  }

public double getJoystickAxisRight(int axisY) {
	return getJoystickAxis(axisY, joystickRight, DEADZONE);
}


public double getJoystickAxisLeft(int axisY) {
	return getJoystickAxis(axisY, joystickLeft, DEADZONE);
}
}