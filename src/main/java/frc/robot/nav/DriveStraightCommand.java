package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class DriveStraightCommand extends CommandBase {

  
  private double currentAngle;
  private double targetDistance;
  private double remainingDistance;
  
  private static final double DEFAULT_PERCENT_OUTPUT = 0.30;
  private static final double MIN_PERCENT_OUTPUT = 0.20;
  private static final double ANGLE_kP = 3;
  private static final double TOLERANCE = 2; // degrees
  private static final double DISTANCE_TOLERANCE = 2;
  private static final double SLOWING_DISTANCE = 20;


  public DriveStraightCommand(double targetDistance) {
    addRequirements(Robot.drivesys);
    this.targetDistance = targetDistance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      Navx.getInstance().reset();
      Navx.getInstance().reset();
      Robot.drivesys.resetEncoders();

      currentAngle = Navx.getInstance().getYaw();
      remainingDistance = targetDistance - Robot.drivesys.getEncoderAverage();

      Robot.drivesys.setTankDrive(DEFAULT_PERCENT_OUTPUT, DEFAULT_PERCENT_OUTPUT);

      // Robot.drivesys.setPeanutLeft(DEFAULT_PERCENT_OUTPUT);
      // Robot.drivesys.setPeanutRight(DEFAULT_PERCENT_OUTPUT);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      double power = DEFAULT_PERCENT_OUTPUT;
      remainingDistance = targetDistance - Robot.drivesys.getEncoderAverage();

      if(remainingDistance <= SLOWING_DISTANCE) {
        power *= (remainingDistance / SLOWING_DISTANCE);
      }

      double powerAdjustment = 0;

      if(Math.abs(currentAngle) >= TOLERANCE) {
        powerAdjustment = (currentAngle / 180) * power * ANGLE_kP;
      }

      Robot.drivesys.setTankDrive(power - powerAdjustment, power + powerAdjustment);
    
      // Robot.drivesys.setPeanutLeft(power - powerAdjustment);
      // Robot.drivesys.setPeanutRight(power + powerAdjustment);
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.setTankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(remainingDistance) <= DISTANCE_TOLERANCE);
  }
}