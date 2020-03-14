/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.nav.drivestraight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.nav.Navx;

public class MecanumDriveStraightAndTurn extends CommandBase {
  private double targetDistance, targetDirection, targetYaw, targetSpeed;
  private double currentDistance, currentDirection, currentYaw;
  private double deltaDistance, deltaDirection, deltaYaw;
  private double aeYaw;

  private static final double Enc0Target = -75;
  private static final double Enc1Target = -50;
  private static final double Enc2Target = 14;
  private static final double Enc3Target = 27.50;
  private static final double ENC_VALUE_TOLERANCE = 1.00;
  
  private static final double YAW_TOLERANCE = 0.4;
  private static final double YAW_kP = 0.3;
  private static final double YAW_kI = 0.000000;

  private static final double DISTANCE_kP = 1;
  private static final double DISTANCE_TOLERANCE = 8;
  private static final double SLOWING_DISTANCE = 20;
  private static final double SLOWING_ADJUSTMENT = 6;
  /**
   * Creates a new MecanumDriveStraightAndTurn.
   */
  public MecanumDriveStraightAndTurn(double targetDistance, double targetDirection, double targetYaw, double targetSpeed) {
    addRequirements(Robot.drivesys);
    this.targetDirection = targetDirection;
    this.targetDistance = targetDistance;
    this.targetYaw = targetYaw;
    this.targetSpeed = targetSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Navx.getInstance().reset();
    Navx.getInstance().reset();
    Robot.drivesys.resetEncoders();
    aeYaw = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  double power = targetSpeed * Math.signum(targetDistance);

  System.out.println("Yaw: " + Navx.getInstance().getAngle());

  //Yaw adjustment
  currentYaw = Navx.getInstance().getAngle();
  // if(targetYaw >= 175 && currentYaw < 0) {
  //   currentYaw = Navx.getInstance().getAngle();
  // } else if (targetYaw <= -175 && currentYaw > 0){
  //   currentYaw = Navx.getInstance().getYaw() - 360;
  // } 
  deltaYaw = -targetYaw + currentYaw;
  double zRotationAdjustment = 0;
  if (Math.abs(deltaYaw) >= YAW_TOLERANCE){
    aeYaw += deltaYaw;
    zRotationAdjustment = ((deltaYaw/180 * YAW_kP) + (aeYaw/180 * YAW_kI) * power);
  } else {
    aeYaw = 0;
  }

  //Speed/Distance Adjustment
  currentDistance = Robot.drivesys.getEncoderAverage(true, 0); //todo, figure out which encoder to use
  deltaDistance = targetDistance - currentDistance;
  if(deltaDistance <= SLOWING_DISTANCE){
    // power *= (deltaDistance + SLOWING_ADJUSTMENT) / (SLOWING_DISTANCE + SLOWING_ADJUSTMENT) * DISTANCE_kP;
  }

  //Direction Adjustment
  currentDirection = currentYaw;
  deltaDirection = targetDirection - currentDirection;

  Robot.drivesys.setPolarDriveSpeed(power, deltaDirection, zRotationAdjustment);
  System.out.println("working");

}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.drivesys.setTankDrive(0, 0);
    System.out.println("BAD");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
