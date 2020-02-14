/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.input.Ports;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

public class PID_Control extends Command {
  Timer timer = new Timer();
  TalonSRX Spinner = new TalonSRX(Ports.RIGHT_MOTOR);

  public PID_Control() {
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
/*    /* Factory Default all hardware to prevent unexpected behaviour */
  //   Spinner.configFactoryDefault();

     /* Config sensor used for Primary PID [Velocity] */
   //  Spinner.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

       /**
    * Phase sensor accordingly. 
        * Positive Sensor Reading should match Green (blinking) Leds on Talon
        */
    // Spinner.setSensorPhase(true);

   // /* Config the peak and nominal outputs */
 /*  Spinner.configNominalOutputForward(0, Constants.kTimeoutMs);
   Spinner.configNominalOutputReverse(0, Constants.kTimeoutMs);
   Spinner.configPeakOutputForward(0.2, Constants.kTimeoutMs);
   Spinner.configPeakOutputReverse(-0.2, Constants.kTimeoutMs);
*\
   // /* Config the Velocity closed loop gains in slot0 */
  //  Spinner.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
  //  Spinner.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
  //  Spinner.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
  //  Spinner.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
   
       timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (timer.hasPeriodPassed(0.25)) {
      System.out.print("Encoder: ");
      System.out.print(Spinner.getSelectedSensorPosition());
      System.out.print(" Speed: ");
      System.out.println(Spinner.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
     }


 //   if (xbox.getAButton()) {
      /* Velocity Closed Loop */
      double targetVelocity_UnitsPer100ms = 130.6;
      Spinner.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
     /* } else if (xbox.getBButton()) {
        Spinner.set(ControlMode.Velocity, 0);
      } */
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
