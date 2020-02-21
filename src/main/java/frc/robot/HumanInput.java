package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CameraCommand;


public class HumanInput {

public static Joystick joystick;
public static JoystickButton button5, button6;
public static JoystickButton cameraSwitchButton;
public static JoystickButton cameraSwitcherooButton;

public HumanInput(){
joystick = new Joystick(2);
button6 = new JoystickButton(joystick, 6);

//cameraSwitchButton = new JoystickButton(joystick, 6);
//cameraSwitcherooButton = new JoystickButton(joystick, 6);


}

public void registerButton(){
    button6.whenPressed(new CameraCommand());
    //cameraSwitchButton.whenPressed(new CameraCommand());
}


}
