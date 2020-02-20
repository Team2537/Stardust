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
import frc.robot.drive.DriveSubsystem;
import frc.robot.input.HumanInput;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShooterSubsystem;
import frc.robot.cameras.Cameras;
import frc.robot.climb.*;

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
  public Cameras cameras;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    humanInput = new HumanInput();
    Robot.intakesys.setSolenoid(true);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
   // m_robotContainer = new RobotContainer();
    humanInput = new HumanInput();
    humanInput.registerButtons();
    cameras = Cameras.getInstance();
    Cameras.getInstance().startCameras();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
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

    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit() {
    Robot.drivesys.resetEncoders();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Robot.drivesys.periodic();
    Robot.intakesys.periodic();
    CommandScheduler.getInstance().run(); 
    double i = ShooterSubsystem.getInstance().getShooterSpeed();
    if(i < - 500) {
      System.out.println(i);
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
    CommandScheduler.getInstance().run();
  }
}
 