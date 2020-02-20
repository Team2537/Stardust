package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;
import frc.robot.drive.SwitchDriveCommand;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.intake.MoveIntakeCommand;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;
  XboxController xbox;
  Button tankButton;
  Button intakeButton;
  JoystickButton shooterButton;
  JoystickButton stopShooterButton;
  JoystickButton startShooterButton;
  
  Button presetPosition;
  Button enableClimb;
  
  public final double climbDEADZONE = .1;
  

  public HumanInput() {
      joystickLeft = new Joystick(0);
      joystickRight = new Joystick(1);
      xbox = new XboxController(Ports.XBOX_CONTROLLER);
      tankButton = new JoystickButton(joystickLeft, Ports.TANK_BUTTON);
      intakeButton = new JoystickButton(xbox, Ports.INTAKE_BUTTON); 
      presetPosition = new JoystickButton(xbox, Ports.PRESET_POSITION_BUTTON);
      enableClimb = new JoystickButton(xbox, Ports.ENABLE_CLIMB_BUTTON);
      shooterButton = new JoystickButton(xbox, 5);
      stopShooterButton = new JoystickButton(xbox, 4);
      startShooterButton = new JoystickButton(xbox, 3);

      //Button to allow winch and telescope to be run
    

  }



  public double getJoystickAxis(int axis, GenericHID joystick, double deadzone){
    double val = joystick.getRawAxis(axis);
    if (Math.abs(val) <= deadzone){
      return 0;
    } else {
      return val;
    }
  }

  public double getXboxJoystickAxis() {
    double val = xbox.getY(Hand.kRight);

    //In order to make sure someone's filthy green sausages don't accidentally hit the joystick,
    //it only runs when pushed passed a certain point
    if(Math.abs(val) > climbDEADZONE) {
      return val;
    }
    else {
      return 0;
    }
  }

  public double getXboxLeftTrigger() {
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
  public void registerButtons(){
    intakeButton.whenPressed(new MoveIntakeCommand()); //Y toggle between true and false
    tankButton.whenPressed(new SwitchDriveCommand());
    shooterButton.whileHeld(ShootingCommandGroup.getInstance(), false);
    shooterButton.whenReleased(new StopShooterCommand());
    stopShooterButton.whenPressed(new StopShooterCommand());
    startShooterButton.whenPressed(ShootingCommandGroup.getInstance());
    enableClimb.whenHeld(new ClimbCommand());
    presetPosition.whenPressed(new PresetPositionCommand());
  
  }

public double getJoystickAxisRight(int axisY) {
	return getJoystickAxis(axisY, joystickRight, DEADZONE);
}

public double getJoystickAxisLeft(int axisY) {
	return getJoystickAxis(axisY, joystickLeft, DEADZONE);
}


}