/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * manages color sensor, motor, and rotation counts for control panel
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
    //motor turns in opposite direction from color wheel
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

  // Competition wheel will spin clockwise
  // ignore colors seen at intersections where colors meet ex: between yellow and
  // blue green is detected
  // this method also counts the number of instances each color has been seen
  // sets last color
  public void pollColorSensor() {
    String detectedColorString = detectColor();
    if (detectedColorString.equals("Red")
        && (lastColor.equals("Yellow") || lastColor.equals("Green") || lastColor.equals(""))) {
      numRed++;
      lastColor = "Red"; // in between red and green, yellow is read. This accounts for that.

    } else if (detectedColorString.equals("Green") && (lastColor.equals("Blue") || lastColor.equals(""))) {
      numGreen++;
      lastColor = "Green";

    } else if (detectedColorString.equals("Blue")
        && (lastColor.equals("Green") || lastColor.equals("Yellow") || lastColor.equals(""))) {
      numBlue++;
      lastColor = "Blue"; // in between blue and yellow, green is read. This accounts for that.

    } else if (detectedColorString.equals("Yellow") && (lastColor.equals("Red") || lastColor.equals(""))) {
      numYellow++;
      lastColor = "Yellow";

    }

  }

  // every time each color has been seen another time, revolutions will increase
  // by 1.
  public void calculateRevolutions() {// moved true false thing to isWheelSpunXTimes
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
    motor.set(-power); // set power to -power for competition wheel
  }

  public String getGameData() {
    // gets assigned color from game data box in the driver station and from the FMS
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

  //must be called from robot periodic
  public void updateSmartDashboard() {
  
    //constantly checks to see if FMS game data has changed 
    setTargetColor(getGameData());

    SmartDashboard.putString("Lastcolor", lastColor);
    SmartDashboard.putString("Detected Color", detectColor());

    SmartDashboard.putString("Assigned target color: ", targetColor);
    SmartDashboard.putString("Robot will stop at", getGameData());


    SmartDashboard.putNumber("Num of Red", NumRed());
    SmartDashboard.putNumber("Num of Blue", NumBlue());
    SmartDashboard.putNumber("Num of Green", NumGreen());
    SmartDashboard.putNumber("Num of yellow", NumYellow());

    SmartDashboard.putNumber("Revolutions", getRevolutions());

  }

  // unused for future implimanetation of PID motor for speed control
  public void PIDMotorInit() {
    WPI_TalonSRX Spinner = new WPI_TalonSRX(Ports.CW_MOTOR);
    Spinner.configFactoryDefault();

    /* Config sensor used for Primary PID [Velocity] */
    Spinner.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    /**
     * Phase sensor accordingly. Positive Sensor Reading should match Green
     * (blinking) Leds on Talon
     */
    Spinner.setSensorPhase(true);

    // /* Config the peak and nominal outputs */
    Spinner.configNominalOutputForward(0, Constants.kTimeoutMs);
    Spinner.configNominalOutputReverse(0, Constants.kTimeoutMs);
    Spinner.configPeakOutputForward(0.2, Constants.kTimeoutMs);
    Spinner.configPeakOutputReverse(-0.2, Constants.kTimeoutMs);

    // /* Config the Velocity closed loop gains in slot0 */
    Spinner.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
    Spinner.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
    Spinner.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
    Spinner.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

  }
}