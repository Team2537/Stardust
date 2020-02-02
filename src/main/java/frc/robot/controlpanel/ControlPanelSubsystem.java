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

/*  private static enum ButtonName {
    YBUTTON, XBUTTON, ABUTTON, BBUTTON, STARTBUTTON, NOBUTTON
  };

  private static ButtonName name;*/

  private static WPI_TalonSRX rightMotor;
  private double rightPower;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final static ColorMatch m_colorMatcher = new ColorMatch();

  private final static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private static String lastColor = "";

  public Color detectedColor;

  int numRed = 0;
  int numBlue = 0;
  int numGreen = 0;
  int numYellow = 0;

  int Revolutions = 0;

  private String colorString = "";

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
  

  public String getColorString() {
    return colorString;
  }

  public String getLastColor() {
    return lastColor;
  }

  public void setLastColor(String color) {
  lastColor = color;
  }



  public String detectColor() {


    detectedColor = m_colorSensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }
   
     return colorString;
  }
  public void detectCorrectColor(String correctColor) {
    correctColor = colorString;
    //lastColour = lastColor;
    if (colorString.equals("Red") && (lastColor.equals("Yellow") || lastColor.equals("Green") || lastColor.equals(""))) {
      numRed++;
      lastColor = "Red";

    } else if (colorString.equals("Green")
        && (lastColor.equals("Red") || lastColor.equals("Yellow") ||  lastColor.equals(""))) {
      /*
       * if (lastColor.equals("Yellow")) { numYellow--; }
       */
      numGreen++;
      lastColor = "Green";

    } else if (colorString.equals("Blue") && (lastColor.equals("Green") || lastColor.equals("Yellow") || lastColor.equals(""))) {
      numBlue++;
      lastColor = "Blue";

    } else if (colorString.equals("Yellow")
        && (lastColor.equals("Blue") || lastColor.equals("Green") || lastColor.equals(""))) {
      /*
       * if (lastColor.equals("Green")) numGreen--;
       */
      numYellow++;
      lastColor = "Yellow";

    }

  }
  
  
  public void CalculateRevolutions() {
    if (numRed >= Revolutions + 1 && numGreen >= Revolutions + 1 && numBlue >= Revolutions + 1
    && numYellow >= Revolutions + 1) {

  Revolutions++;
}
if (Revolutions == 8) {
  rightPower = 0.0;
  rightMotor.set(rightPower);
}
  }

  public int getRevolutions() {
    return Revolutions;
  }
  public int setRevolutions() {
     Revolutions++;
     return Revolutions;
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
  public void ZeroNumRed() {
    numRed = 0;
  }
  public void ZeroNumGreen() {
    numGreen = 0;
  }
  public void ZeroNumBlue() {
    numBlue = 0;
  }
  public void ZeroNumYellow() {
    numYellow = 0;
  }
  public void ZeroRevolutions() {
    Revolutions = 0;
  }
 
  

  public void stopMotors() {
    rightPower = 0.0;
  rightMotor.set(rightPower);
  }
  public void startMotors() {
    rightPower = 0.2;
    rightMotor.set(rightPower);
  }






 

  
}