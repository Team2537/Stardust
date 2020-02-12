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
import edu.wpi.first.wpilibj.DriverStation;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.input.Ports;
import frc.robot.controlpanel.PID_Control;
import edu.wpi.first.wpilibj.Timer;


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

 // private static WPI_TalonSRX rightMotor;
  private static TalonSRX testMotor;
 // private double rightPower;
  private double testPower;
  

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final static ColorMatch m_colorMatcher = new ColorMatch();

  private final static Color kBlueTarget = ColorMatch.makeColor (.14, .47, .38);
  private final static Color kGreenTarget = ColorMatch.makeColor(.23, .63, .13);
  private final static Color kRedTarget = ColorMatch.makeColor(.51, .35, .13);
  private final static Color kYellowTarget = ColorMatch.makeColor(.32, .56, .11);

  private String lastColor = "";
  private String targetColor;
  private String detectedColorString = "";

  int numRed = 0;
  int numBlue = 0;
  int numGreen = 0;
  int numYellow = 0;

  int revolutions = 0;

  double targetVelocity_UnitsPer100Ms;
  Timer timer = new Timer();
  TalonSRX Spinner = new TalonSRX(Ports.RIGHT_MOTOR);


  private ControlPanelSubsystem() {

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

   // rightMotor = new WPI_TalonSRX(Ports.RIGHT_MOTOR);
    testMotor = new TalonSRX(Ports.RIGHT_MOTOR);
    // right motor turns in opposite direction
   // rightMotor.setInverted(true);
    testMotor.setInverted(false);
    testMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    


     /* Factory Default all hardware to prevent unexpected behaviour */
     Spinner.configFactoryDefault();

     /* Config sensor used for Primary PID [Velocity] */
     Spinner.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

       /**
    * Phase sensor accordingly. 
        * Positive Sensor Reading should match Green (blinking) Leds on Talon
        */
     Spinner.setSensorPhase(true);

   // /* Config the peak and nominal outputs */
   Spinner.configNominalOutputForward(0, Constants.kTimeoutMs);
   Spinner.configNominalOutputReverse(0, Constants.kTimeoutMs);
   Spinner.configPeakOutputForward(0.25, Constants.kTimeoutMs);
   Spinner.configPeakOutputReverse(-0.2, Constants.kTimeoutMs);

   // /* Config the Velocity closed loop gains in slot0 */
   Spinner.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
   Spinner.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
   Spinner.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
   Spinner.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
   
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
    System.out.println("Target Color: "+targetColor);
  }

  public void detectColor() { // finds the current color that the sensor sees

    Color detectedColor = m_colorSensor.getColor(); // grab raw color values from sensor

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor); // match it to find a color
    System.out.println(m_colorSensor.getProximity());
    System.out.println(m_colorSensor.getRawColor());

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
      testPower = 0.0;
      Spinner.set(ControlMode.Velocity, testPower);
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
    testPower = 0.0;
    testMotor.set(ControlMode.Velocity, testPower);
  }

  public void startPIDMotors() {
   // testPower = -0.3; // should spin clockwise when looking down form the top
    //colors in order that should be seen: G, R, Y, B
   // testMotor.set(ControlMode.Velocity, _PIDControl());
   _PIDControl();
    System.out.println("started PID motors");
  }

  /*public double PIDIThink() {
    targetVelocity_UnitsPer100Ms = testPower * 120 * 103.6 / 600;
    testMotor.set(ControlMode.Velocity, targetVelocity_UnitsPer100Ms);
    System.out.println("Testpower works------");
    return targetVelocity_UnitsPer100Ms;
  }*/


  public boolean spinToColor() {
   // PIDIThink();
    //System.out.println("Testpower: "+PIDIThink());
    setCorrectColor();
    if (lastColor.equals(targetColor)) {
      // last color is the current color in this scenario.
      // when the setCorrectColor method is called, it sets the current color to the
      // lastColor String.
      System.out.println(lastColor + "  " + targetColor);

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
  public String getTargetColor() {
    return targetColor;
  }
  public void _PIDControl() {
    timer.start();
    System.out.println("Hello World");
    if (timer.hasPeriodPassed(0.25)) {
      System.out.print("Encoder: ");
      System.out.print(Spinner.getSelectedSensorPosition());
      System.out.print(" Speed: ");
      System.out.println(Spinner.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
     }

    double targetVelocity_UnitsPer100ms = 130.6;
    Spinner.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
    
  }
  public void startMotors() {
    Spinner.set(ControlMode.Velocity, 0.2);
  }
}