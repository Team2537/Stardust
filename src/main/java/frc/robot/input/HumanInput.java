package frc.robot.input;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.controlpanel.SpinToColorCommand;
import frc.robot.controlpanel.SpinXTimesCommand;

public class HumanInput {
  XboxController xbox = new XboxController(0);
  Button setColorRedBtn, setColorGreenBtn, setColorBlueBtn, setColorYellowBtn, spinToGameDataBtn;

  Button spinXTimes;
  
  public HumanInput() {
    setColorRedBtn = new JoystickButton(xbox, 1); // A
    setColorGreenBtn = new JoystickButton(xbox, 2); // B
    setColorBlueBtn = new JoystickButton(xbox, 3); // X
    setColorYellowBtn = new JoystickButton(xbox, 4); // Y

    spinToGameDataBtn = new JoystickButton(xbox, 5); // L Trigger button

    spinXTimes = new JoystickButton(xbox, 8); // this is the "start" button
  }
 // setColor calls are commented out to test inputing color data using game data

 public void registerButtons() {
    /*setColorRed.whenPressed(new SpinToColorCommand("Green"));
    setColorGreen.whenPressed(new SpinToColorCommand("Red"));
    setColorBlue.whenPressed(new SpinToColorCommand("Blue"));
    setColorYellow.whenPressed(new SpinToColorCommand("Yellow"));
    */
    spinXTimes.whenPressed(new SpinXTimesCommand()); 
  }

  // New method for game data entry
  public void getGameDataBtn(){
    if(Robot.controlsubsys.isGameDataValid() == true){
      spinToGameDataBtn.whenPressed(new SpinToColorCommand());
    }
  }

  //public String registerColorInput(){

    /*gameData = DriverStation.getInstance().getGameSpecificMessage().toUpperCase();

      if(gameData.length() > 0) {
        switch (gameData.charAt(0)) {
          case 'B' :
          getGameData.whenPressed(new SpinToColorCommand("Blue"));
          System.out.println("BLUE BLUE BLUE BLUE"); 
  
            break;
          case 'G' :
          getGameData.whenPressed(new SpinToColorCommand("Green"));
          System.out.println("GREN GREN GREN GREN GREN"); 
  
            break;
          case 'R' :
          getGameData.whenPressed(new SpinToColorCommand("Red"));
          System.out.println("RED RED RED RED RED RED"); 
  
            break;
          case 'Y' :
          getGameData.whenPressed(new SpinToColorCommand("Yellow"));
          System.out.println("YELLER YELLER YELLER YELLER"); 
  
            break;

          default :
          Robot.controlsubsys.stopMotors();
           gameData = "default";
            break;
        }
      } else {
        Robot.controlsubsys.stopMotors();
        System.out.println("I'm empty inside TT"); 
        gameData = "empty";
      }

    System.out.println(gameData);
    Robot.controlsubsys.setGameData(gameData);
  }*/

}