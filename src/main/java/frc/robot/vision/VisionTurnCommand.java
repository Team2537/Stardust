/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class VisionTurnCommand extends CommandBase {
  public static final int LOWERTHRESHOLD = 300; 
  public static final int HIGHERTHRESHOLD = 340;
  public static final int OUTERLOWERTHRESHOLD = 200; 
  public static final int OUTERHIGHERTHRESHOLD = 440;
  public static final double TURNSPEED = 0.15;
  private Point MidPoint;
  private Target[] targets;
  private double half = 320; //midpoint of screen
  /**
   * Creates a new VisionTurnCommand.
   */
  public VisionTurnCommand() {
    addRequirements(Robot.drivesys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("We are now one turny boi");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    targets = Robot.udpsys.getVisionPacket();
        MidPoint = Target.getMidpoint(targets);
        
        System.out.println("MIDPOINT: "+ MidPoint);
        
        if(MidPoint.getX() > HIGHERTHRESHOLD || MidPoint.getX() < LOWERTHRESHOLD){

           if(MidPoint.getX() > OUTERLOWERTHRESHOLD && MidPoint.getX() < OUTERHIGHERTHRESHOLD) {
               System.out.println("OUTER THRESHOLD REACHED!!");

                //slows down proportionally to distance from midpoint
                if(MidPoint.getX() < half) { // if midpoint is too far left
                    Robot.drivesys.setTankDrive(-TURNSPEED*((half-MidPoint.getX()))/120, TURNSPEED*(half-MidPoint.getX())/120.0);
                }

                //slows down proportionally to distance from midpoint
                if(MidPoint.getX() > half) { // if midpoint is too far right
                    Robot.drivesys.setTankDrive(TURNSPEED*((MidPoint.getX()-half)/120.0), -TURNSPEED*((MidPoint.getX()-half)/120.0));
                }

            }
            
            else{
                Robot.drivesys.setTankDrive(TURNSPEED, -TURNSPEED);
            }
        }
        else{
            Robot.drivesys.setTankDrive(0, 0);
        }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
