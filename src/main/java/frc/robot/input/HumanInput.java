package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button manuallySpinBtn, spinToColorBtn, spinXTimesBtn;
  boolean gameDataRegistered = false;
  boolean manualSpinRegistered = false;

  
  public HumanInput() {
    // Manual spin button is in Robot teleopPeriodic its the right trigger
    spinToColorBtn = new JoystickButton(xbox, 2); // B
    spinXTimesBtn = new JoystickButton(xbox, 3); // X 

    registerSpinXTimesBtn();
    registerGameDataBtn();
  }

 public void registerSpinXTimesBtn() {
    spinXTimesBtn.whenPressed(new SpinXTimesCommand()); 
  }

  // Manually entered color should only be the first letter of the color
  // Manually entered color in game data only enters when
  // you click out of the box you typed it in
  public void registerGameDataBtn(){
    spinToColorBtn.whenPressed(new SpinToColorCommand());
  }

  public double getXboxTriggerPos(){
    return xbox.getTriggerAxis(Hand.kRight);
  }

}