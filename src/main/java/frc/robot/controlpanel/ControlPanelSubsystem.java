/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This manages color sensor, motor, keeps track of rotations, and what the
 * current color is
 */
public class ControlPanelSubsystem extends SubsystemBase {

  private static ControlPanelSubsystem instance = null;

  private WPI_TalonSRX motor;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.14, 0.47, 0.38);
  private final Color kGreenTarget = ColorMatch.makeColor(0.23, 0.63, 0.13);
  private final Color kRedTarget = ColorMatch.makeColor(0.51, 0.35, 0.13);
  private final Color kYellowTarget = ColorMatch.makeColor(0.32, 0.56, 0.11);
  public final double cwDEADZONE = 0.25;

  private String lastColor = "";
  private String targetColor = "";

  int numRed = 0;
  int numBlue = 0;
  int numGreen = 0;
  int numYellow = 0;

  int revolutions = 0;

  private ControlPanelSubsystem() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    motor = new WPI_TalonSRX(Ports.CW_MOTOR);
    // motor turns in opposite direction from color wheel
    motor.configFactoryDefault();
    motor.setInverted(true);
  }

  // Singleton design pattern
  public static ControlPanelSubsystem getInstance() {
    if (instance == null) {
      instance = new ControlPanelSubsystem();
    }

    return instance;

  }

  // changes color to account for positioning targetColor to game's sensor by 2
  public void setTargetColor(String color) {
    switch (color) {
    case "Blue":
      targetColor = "Red";
      break;

    case "Green":
      targetColor = ("Yellow");
      break;

    case "Red":
      targetColor = ("Blue");
      break;

    case "Yellow":
      targetColor = ("Green");
      break;
    }
  }

  // assigns raw color sensor values to global variable detectedColorString
  public String detectColor() { // finds the current color that the sensor sees

    Color detectedColor = m_colorSensor.getColor(); // grab raw color values from sensor

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor); // match it to find a color

    if (match.color == kBlueTarget) {
      return "Blue";
    } else if (match.color == kRedTarget) {
      return "Red";
    } else if (match.color == kGreenTarget) {
      return "Green";
    } else if (match.color == kYellowTarget) {
      return "Yellow";
    } else {
      return "Unknown";
    }

  }

  // pollColorSensor only works when colors are seen in a specific order
  // Order of colors must be: RED, GREEN, BLUE, YELLOW, RED...

  // ignore colors seen at intersections where colors meet ex: between yellow and
  // blue green is detected
  // this method also counts the number of instances each color has been seen
  // sets last color
  public void pollColorSensor() {
    String detectedColorString = detectColor();
    if (detectedColorString.equals("Red")
        && (lastColor.equals("Yellow") || lastColor.equals("Green") || lastColor.equals(""))) {
      numRed++;
      lastColor = "Red"; // In between red and green, yellow is read. This accounts for that.

    } else if (detectedColorString.equals("Green") && (lastColor.equals("Blue") || lastColor.equals(""))) {
      numGreen++;
      lastColor = "Green";

    } else if (detectedColorString.equals("Blue")
        && (lastColor.equals("Green") || lastColor.equals("Yellow") || lastColor.equals(""))) {
      numBlue++;
      lastColor = "Blue"; // In between blue and yellow, green is read. This accounts for that.

    } else if (detectedColorString.equals("Yellow") && (lastColor.equals("Red") || lastColor.equals(""))) {
      numYellow++;
      lastColor = "Yellow";

    }

  }

  // Every time each color has been seen another time, revolutions will increase
  // by 1.
  public void calculateRevolutions() {
    if (numRed >= revolutions + 1 && numGreen >= revolutions + 1 && numBlue >= revolutions + 1
        && numYellow >= revolutions + 1) {

      revolutions++;
    }
  }

  public int getRevolutions() {
    return revolutions;
  }

  public int NumRed() {
    return numRed;
  }

  public int NumGreen() {
    return numGreen;
  }

  public int NumBlue() {
    return numBlue;
  }

  public int NumYellow() {
    return numYellow;
  }

  public void zeroCounters() {
    numRed = 0;
    numGreen = 0;
    numBlue = 0;
    numYellow = 0;
    revolutions = 0;
  }

  public void stopMotors() {
    motor.set(0.0);
  }

  public void startMotors(double power) {
    // Competition wheel must spin clockwise
    motor.set(power);
  }

  public String getGameData() {
    // Gets assigned color from game data box in the driver station and from the FMS
    String gameData = DriverStation.getInstance().getGameSpecificMessage().toUpperCase();

    if (gameData.length() == 0) {
      return "Empty";
    }
    switch (gameData.charAt(0)) {
    case 'B':
      return ("Blue");

    case 'G':
      return ("Green");

    case 'R':
      return ("Red");

    case 'Y':
      return ("Yellow");

    default:
      return "Unknown";
    }

  }

  public boolean isOnTargetColor() {
    pollColorSensor();

    return (lastColor.equals(targetColor));
    // last color is the current color in this scenario.
    // when the pollColorSensor method is called, it sets the current color to the
    // lastColor String.
    // System.out.println("Target Color " + lastColor + " has been found ");

  }

  public boolean isWheelSpun4Times() {
    pollColorSensor();
    calculateRevolutions();

    if (revolutions >= 8) {
      stopMotors();
      return true;
    } else {
      return false;
    }
  }

  // Must be called from robot periodic
  public void updateSmartDashboard() {

    // Constantly checks to see if FMS game data has changed
    setTargetColor(getGameData());

    SmartDashboard.putString("Last color:", lastColor);
    SmartDashboard.putString("Detected Color:", detectColor());

    //  Theoretically this code is in the merged code now
    SmartDashboard.putString("DriverTiles/Target Color: ", targetColor);
    SmartDashboard.putString("DriverTiles/Stop at Color:", getGameData());
    SmartDashboard.putNumber("DriverTiles/Revolutions:", getRevolutions());
    
    SmartDashboard.putNumber("Num of Red:", NumRed());
    SmartDashboard.putNumber("Num of Blue:", NumBlue());
    SmartDashboard.putNumber("Num of Green:", NumGreen());
    SmartDashboard.putNumber("Num of yellow:", NumYellow());

    // To add a tile to a different tab set the source prefix to 
    //SmartDashboard/whatEverYouWant then in the parameter where
    // you name the tile type whatEverYouWantname 
 }

  // Checks if a control panel command is running or if command is null 
  //and interupts the command to starts the motor at triggerPos value or
  public void periodic(){
   double joystickPos = Robot.humanInput.xbox.getY(Hand.kLeft);

   Command c = getCurrentCommand();
   boolean isSpinCommandRunning = (c != null) && ((c instanceof SpinXTimesCommand)|| (c instanceof SpinToColorCommand));

   if(Math.abs(joystickPos) > cwDEADZONE) {     
    if(isSpinCommandRunning) { 
      c.cancel();
      stopMotors();
    }
    startMotors(joystickPos / 3);

   } else if (!isSpinCommandRunning) {
     stopMotors();
   }
  }
}
