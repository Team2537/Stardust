package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;


public class CustomDashboardLogger {

    public void log() {

        SmartDashboard.putString("Target Color", Robot.controlsubsys.getTargetColor());
        SmartDashboard.putString("Stop At", Robot.controlsubsys.getGameData());
        SmartDashboard.putNumber("Revolutions", Robot.controlsubsys.getRevolutions());
        SmartDashboard.putNumber("# of Balls", Robot.shooter.getBallCount());
        SmartDashboard.putString("Intake Wheels", Robot.intakesys.getMotorStatus() ? "ON" : "OFF");

    }

}