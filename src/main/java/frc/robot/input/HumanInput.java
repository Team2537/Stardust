package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button manuallySpinBtn, spinToGameDataBtn, spinXTimes;
  boolean gameDataRegistered = false;
  boolean manualSpinRegistered = false;

  
  public HumanInput() {
    spinToGameDataBtn = new JoystickButton(xbox, 2); // B
    spinXTimes = new JoystickButton(xbox, 3); // X 
  }


 public void registerSpinXTimesBtn() {
    spinXTimes.whenPressed(new SpinXTimesCommand()); 
  }

  public void registerGameDataBtn(){
      spinToGameDataBtn.whenPressed(new SpinToColorCommand());

  }

  public double getXboxTriggerPos(){
    return xbox.getTriggerAxis(Hand.kRight);
  }

}