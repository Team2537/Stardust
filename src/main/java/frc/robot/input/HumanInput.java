package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;

public class HumanInput {
  static XboxController xbox = new XboxController(0);
  Button openButton = new JoystickButton(xbox, Ports.OPEN_BUTTON);
  Button closeButton = new JoystickButton(xbox, Ports.CLOSE_BUTTON);
  static JoystickButton shooterButton = new JoystickButton(xbox, 5);
  static JoystickButton stopShooterButton = new JoystickButton(xbox, 4);
  static JoystickButton startShooterButton = new JoystickButton(xbox, 3);
  //static JoystickButton stopShooterButton = new JoystickButton(xbox, 4);

  public HumanInput() {

  }

  public static void registerButtons() {

    shooterButton.whileHeld(ShootingCommandGroup.getInstance(), false);
    shooterButton.whenReleased(new StopShooterCommand());
    stopShooterButton.whenPressed(new StopShooterCommand());
    startShooterButton.whenPressed(ShootingCommandGroup.getInstance());



















    
    //stopShooterButton.whenPressed(new StopShooterCommand());

  }

}