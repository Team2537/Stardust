/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.nav.Navx;
import frc.robot.util.PID;

public class VisionSuperCommand extends CommandBase {
  /**
   * Creates a new VisionSuperCommand.
   */
  private double thetaEquation, thetaCurrent, thetaTarget, xCurrent, xStart, y, z;
  private PID thetaPid, xPid;
  public VisionSuperCommand() {
    addRequirements(Robot.drivesys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    thetaTarget = 0;
    thetaCurrent = Navx.getInstance().getYaw();
    z = 2; //needs to be set to LIDAR
    xStart = z * Math.sin(thetaCurrent);
    thetaPid = new PID(0, 0, 0);
    thetaPid.setSetpoint(thetaTarget);
    xPid = new PID(0, 0, 0);
    xPid.setSetpoint(xStart);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    thetaCurrent = Navx.getInstance().getYaw();
    thetaCurrent = thetaCurrent - thetaTarget;
    thetaPid.update(thetaCurrent);
    z = 2; //needs to be set to LIDAR
    xCurrent = xStart - (z * Math.sin(thetaEquation));
    xPid.update(xCurrent);
    Robot.drivesys.setPolarDriveSpeed(xPid.getOutput(), 90-thetaCurrent, thetaPid.getOutput());
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.drivesys.setMecanumDriveSpeed(0, 0, 0);
    System.out.println("all done");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (thetaPid.withinTolerance(0) && xPid.withinTolerance(0)); // todo set these
  }
}
