/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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
import frc.robot.nav.AutoChooser;
import frc.robot.nav.Navx;
import frc.robot.nav.drivestraight.MecanumDriveStraightAndTurn;
import frc.robot.controlpanel.ControlPanelSubsystem;
import frc.robot.controlpanel.SpinXTimesCommand;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static HumanInput humanInput;
  public static DriveSubsystem drivesys = DriveSubsystem.getInstance();

  public static IntakeSubsystem intakesys = IntakeSubsystem.getInstance();

  public static ShooterSubsystem shooter = ShooterSubsystem.getInstance();

  public static ClimbSubsystem climbsys = ClimbSubsystem.getInstance();
  
  public static ControlPanelSubsystem controlsubsys = ControlPanelSubsystem.getInstance();

  public static AutoChooser autoChooser = AutoChooser.getInstance();
  
  public Cameras cameras;
  
  @Override
  public void robotInit() {
    humanInput = new HumanInput();
    humanInput.registerButtons();

    //Robot.intakesys.setSolenoid(true); //starts with intake down

    
    cameras = Cameras.getInstance();
    Cameras.getInstance().startCameras();

    SmartDashboard.putData("Control Panel Motor Test ", new SpinXTimesCommand());// full power
    SmartDashboard.putString("Control Panel Color Sensor Test ", Robot.controlsubsys.detectColor());

  }


  @Override
  public void robotPeriodic() {
    //Robot.shooter.ballIntakeCount();
    //System.out.println("Yaw" + Navx.getInstance().getYaw());
    CommandScheduler.getInstance().run();

    Robot.controlsubsys.updateSmartDashboard();
    Robot.drivesys.putEncodersToDash();
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
    Navx.getInstance().zeroYaw();
    Navx.getInstance().reset();
    Navx.getInstance().reset();
    // CommandScheduler.getInstance().schedule(new TestPath());
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    
    CommandScheduler.getInstance().schedule(new MecanumDriveStraightAndTurn(121, 319, 180, 0.4));

    // CommandScheduler.getInstance().schedule(AutoChooser.getInstance().getPath());
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Robot.drivesys.putEncodersToDash();
    SmartDashboard.putNumber("Navx Yaw", Navx.getInstance().getYaw());
    CommandScheduler.getInstance().run();
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit() {
    Navx.getInstance().reset();
    Robot.drivesys.resetEncoders();
    CommandScheduler.getInstance().schedule(new LoadBallCommand());
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Robot.drivesys.periodic();
    Robot.intakesys.periodic();
    Robot.controlsubsys.periodic();
    CommandScheduler.getInstance().run(); 
    Robot.drivesys.putEncodersToDash();
    
  }

  @Override
  public void testInit() {
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
 


  
  


