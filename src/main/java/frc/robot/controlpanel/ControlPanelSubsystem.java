/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

//import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;

/**
 * Add your docs here.
 */
public class ControlPanelSubsystem extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static ControlPanelSubsystem instance = null;

  /*
   * private static enum ButtonName { YBUTTON, XBUTTON, ABUTTON, BBUTTON,
   * STARTBUTTON, NOBUTTON };
   * 
   * private static ButtonName name;
   */

  private static WPI_TalonSRX rightMotor;
  private double rightPower;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final static ColorMatch m_colorMatcher = new ColorMatch();

  private final static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private String lastColor = "";
  private String targetColor;
  private String detectedColorString = "";

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

    rightMotor = new WPI_TalonSRX(Ports.RIGHT_MOTOR);
    // right motor turns in opposite direction
    rightMotor.setInverted(true);

  }

  // Singleton design pattern
  public static ControlPanelSubsystem getInstance() {
    if (instance == null) {
      instance = new ControlPanelSubsystem();
    }

    return instance;

  }

  public void setTargetColor(String targetColor) {
    this.targetColor = targetColor;
    lastColor = "";
  }

  public void detectColor() { // finds the current color that the sensor sees

    Color detectedColor = m_colorSensor.getColor(); // grab raw color values from sensor

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor); // match it to find a color

    if (match.color == kBlueTarget) {
      detectedColorString = "Blue";
    } else if (match.color == kRedTarget) {
      detectedColorString = "Red";
    } else if (match.color == kGreenTarget) {
      detectedColorString = "Green";
    } else if (match.color == kYellowTarget) {
      detectedColorString = "Yellow";
    } else {
      detectedColorString = "Unknown";
    }

  }

  public void setCorrectColor() {
    detectColor();
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

  public boolean calculateRevolutions() {
    if (numRed >= revolutions + 1 && numGreen >= revolutions + 1 && numBlue >= revolutions + 1
        && numYellow >= revolutions + 1) {

      revolutions++;
    }
    if (revolutions == 8) {
      rightPower = 0.0;
      rightMotor.set(rightPower);
      return true;
    } else {
      return false;
    }
  }

  public int getRevolutions() {
    return revolutions;
  }

  public int setRevolutions() {
    revolutions++;
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
    rightPower = 0.0;
    rightMotor.set(rightPower);
  }

  public void startMotors() {
    rightPower = 0.2;
    rightMotor.set(rightPower);
  }

  public boolean spinToColor() {
    setCorrectColor();
    if (lastColor == targetColor) {
      // last color is the current color in this scenario.
      // when the setCorrectColor method is called, it sets the current color to the
      // lastColor String.

      return true;
    } else {
      return false;
    }
  }

  public boolean spinXTimes() {
    setCorrectColor();
    return calculateRevolutions();

  }
  public String detectedColorString() {
    return detectedColorString;
  }
  public String getLastColor() {
    return lastColor;
  }

}