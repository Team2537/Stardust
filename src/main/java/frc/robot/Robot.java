/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.nav.TestCommand;
import frc.robot.drive.DriveSubsystem;
import frc.robot.drive.SwitchDriveCommand;
import frc.robot.drive.TempDriveCommand;
import frc.robot.drive.DriveSubsystem.DriveMode;
import frc.robot.input.HumanInput;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShooterSubsystem;
import frc.robot.cameras.Cameras;
import frc.robot.climb.*;
import frc.robot.nav.DriveStraightCommand;
import frc.robot.nav.MecanumDriveStraightCommand;
import frc.robot.nav.Navx;
import frc.robot.nav.RotateCommand;

import frc.robot.controlpanel.ControlPanelSubsystem;
import frc.robot.input.Ports;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static HumanInput humanInput;
  public static DriveSubsystem drivesys = DriveSubsystem.getInstance();

  public static IntakeSubsystem intakesys = IntakeSubsystem.getInstance();

  public static ShooterSubsystem shooter = ShooterSubsystem.getInstance();

  public static ClimbSubsystem climbsys = ClimbSubsystem.getInstance();
  
  public static ControlPanelSubsystem controlsubsys = ControlPanelSubsystem.getInstance();

  public Cameras cameras;
  
  @Override
  public void robotInit() {
    humanInput = new HumanInput();
    humanInput.registerButtons();

    //Robot.intakesys.setSolenoid(true); //starts with intake down

    
    cameras = Cameras.getInstance();
    Cameras.getInstance().startCameras();


  }


  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().schedule(new LoadBallCommand());
    Navx.getInstance().zeroYaw();
    Navx.getInstance().reset();
   
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    //CommandScheduler.getInstance().schedule(/*new SwitchDriveCommand(), */new DriveStraightCommand(100));
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }

    Robot.drivesys.putEncodersToDash();
    System.out.println(Navx.getInstance().getYaw());
    CommandScheduler.getInstance().run();
    
  }
/*
  @Override
  public void teleopInit() {
    System.out.print("Starting teleop");
    super.teleopInit();
    System.out.print("Starting teleop");
  }
  */


  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit() {
    Navx.getInstance().reset();
    Robot.drivesys.resetEncoders();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Robot.drivesys.periodic();
    Robot.intakesys.periodic();
    CommandScheduler.getInstance().run(); 
    double i = ShooterSubsystem.getInstance().getShooterSpeed();
    if(i < -500) {
      System.out.println(i);
    }

      //Spins manually when trigger is pressed
  if(Robot.controlsubsys.getCurrentCommand() == null) {
    double triggerPos = Robot.humanInput.getXboxRightTrigger();

    if (triggerPos > 0){
      Robot.controlsubsys.startMotors(triggerPos);
    } else {
     Robot.controlsubsys.stopMotors();
    }
  }
    //ShooterSubsystem.automaticallySetProperSpeed(150);
  }


  @Override
  public void testInit() {

    ShooterSubsystem.getInstance();

    CommandScheduler.getInstance().run();
  }

  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    System.out.println(Navx.getInstance().getYaw());
    CommandScheduler.getInstance().run(); 
  }

}
 


  
  


