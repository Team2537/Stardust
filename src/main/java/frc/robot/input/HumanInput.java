package frc.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.climb.*;
import frc.robot.drive.SwitchDriveCommand;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.cameras.CameraCommand;
import frc.robot.intake.IntakeCommandGroup;
import frc.robot.intake.IntakeMotorCommand;
import frc.robot.intake.MoveIntakeCommand;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;

public class HumanInput {
  public static final int AXIS_X = 0, AXIS_Y = 1, AXIS_Z = 2;
  public static final double DEADZONE = 0.05;
  public final Joystick joystickLeft, joystickRight;
  public XboxController xbox;
  Button tankButton;
  Button intakeButton;
  JoystickButton fullAutoShooterButton;
  JoystickButton stopShooterButton;
  JoystickButton semiAutoShooterButton;
  public static JoystickButton cameraSwitchButton;

  //// controlpanel
  Button manuallySpinBtn, spinToColorBtn, spinXTimesBtn;
  boolean gameDataRegistered = false;
  boolean manualSpinRegistered = false;

  Button presetClimbPosition;
  Button enableClimb;

  public final double climbDEADZONE = .1;
  
  private static JoystickButton realignButton, rotate90BUtton, rotateneg90Button, driveStraightButton, testPathButton, runIntakeButton;/*, testCom*/;

  
  public HumanInput() {
      joystickLeft = new Joystick(Ports.JOYL);
      joystickRight = new Joystick(Ports.JOYR);

      xbox = new XboxController(Ports.XBOX_CONTROLLER);
    
      //JOYSTICK KOLYA
      cameraSwitchButton = new JoystickButton(joystickLeft, Ports.CAMERA_BUTTON);
      tankButton = new JoystickButton(joystickLeft, Ports.TANK_BUTTON);
      
     
      //XBOX KINSLEY
      intakeButton = new JoystickButton(xbox, Ports.STOP_SHOOTER_BUTTON); //Y //checked

      presetClimbPosition = new JoystickButton(xbox, Ports.PRESET_CLIMB_POSITION_BUTTON); //RB //checked
      enableClimb = new JoystickButton(xbox, Ports.ENABLE_CLIMB_BUTTON); //LB hold and use LT to set winch speed and use right joystick for telescope speed//checked

      fullAutoShooterButton = new JoystickButton(xbox, Ports.SHOOTER_BUTTON); //A //checked //hold and shoots until released

      semiAutoShooterButton = new JoystickButton(xbox, Ports.START_SHOOTER_BUTTON); //START checked //shoots once
      //stopShooterButton = new JoystickButton(xbox, Ports.STOP_SHOOTER_BUTTON); //BACK checked
      
      spinXTimesBtn = new JoystickButton(xbox, Ports.SPIN_X_TIMES_BUTTON); //X checked
      spinToColorBtn = new JoystickButton(xbox, Ports.SPIN_TO_COLOR_BUTTON); // B checked
      //use RT to control speed of spin manually

      //Button to allow winch and telescope to be run

      runIntakeButton = new JoystickButton(xbox, Ports.INTAKE_BUTTON);
    
      
      
  


  }



  public double getJoystickAxis(int axis, GenericHID joystick, double deadzone) {
    double val = joystick.getRawAxis(axis);

    if (Math.abs(val) <= deadzone){
      return 0;
    } else {
      return val;
    }
  }

  public double getXboxRightJoystickAxis() {
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
    cameraSwitchButton.whenPressed(new CameraCommand());
    intakeButton.whenPressed(new MoveIntakeCommand()); //Y toggle between true and false
    tankButton.whenPressed(new SwitchDriveCommand());
    
    fullAutoShooterButton.whileHeld(ShootingCommandGroup.getInstance(), false);
    fullAutoShooterButton.whenReleased(new StopShooterCommand());
    fullAutoShooterButton.whenReleased(new LoadBallCommand());
    //stopShooterButton.whenPressed(new StopShooterCommand());
    semiAutoShooterButton.whenPressed(ShootingCommandGroup.getInstance());
    
    enableClimb.whenHeld(new ClimbCommand());
    presetClimbPosition.whenPressed(new PresetPositionCommand());
    
    spinXTimesBtn.whenPressed(new SpinXTimesCommand()); 
    spinToColorBtn.whenPressed(new SpinToColorCommand());
  
    runIntakeButton.toggleWhenPressed(new IntakeMotorCommand());
  }

public double getJoystickAxisRight(int axis) {
	return getJoystickAxis(axis, joystickRight, DEADZONE);
}

public double getJoystickAxisLeft(int axis) {
	return getJoystickAxis(axis, joystickLeft, DEADZONE);
}



}