package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CameraCommand;
import frc.robot.commands.TelescopeCameraCommand;

public class HumanInput {

public static Joystick joystick;
public static JoystickButton button5, button6;

public HumanInput(){
joystick = new Joystick(0);
button5 = new JoystickButton(joystick, 5);
button6 = new JoystickButton(joystick, 6);

}

public void registerButton(){
    button5.whenPressed(new CameraCommand());
    button6.whenPressed(new TelescopeCameraCommand());
}


}
