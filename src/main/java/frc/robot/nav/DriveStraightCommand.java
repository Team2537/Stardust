package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class DriveStraightCommand extends CommandBase {

  
  private double currentAngle;
  private double targetDistance;
  private double remainingDistance;
  private double percentOutputLeft;
  private double percentOutputRight;
  
  private static final double DEFAULT_PERCENT_OUTPUT = 0.70;
  private static final double MIN_PERCENT_OUTPUT = 0.40;
  private static final double ANGLE_kP = .15;
  private static final double SLOWING_ADJUSTMENT = 0;
  private static final double TOLERANCE = 1; // degrees
  private static final double DISTANCE_TOLERANCE = 2;
  private static final double SLOWING_DISTANCE = 30;


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
      remainingDistance = targetDistance - Robot.drivesys.getEncoderDistance();

      Robot.drivesys.setTankDrive(-DEFAULT_PERCENT_OUTPUT, -DEFAULT_PERCENT_OUTPUT);

      // Robot.drivesys.setPeanutLeft(DEFAULT_PERCENT_OUTPUT);
      // Robot.drivesys.setPeanutRight(DEFAULT_PERCENT_OUTPUT);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      double power = DEFAULT_PERCENT_OUTPUT;
      remainingDistance = targetDistance - Robot.drivesys.getEncoderDistance();
      System.out.println("Encoder" + Robot.drivesys.getEncoderDistance());

      if(remainingDistance <= SLOWING_DISTANCE) {
        power *= (remainingDistance + SLOWING_ADJUSTMENT) / (SLOWING_DISTANCE + SLOWING_ADJUSTMENT);
      }

      double powerAdjustment = 0;

      if(Math.abs(currentAngle) >= TOLERANCE) {
        powerAdjustment = (currentAngle / 180) * power * ANGLE_kP;
      }
      power = Math.max(Math.abs(power), MIN_PERCENT_OUTPUT);

      Robot.drivesys.setTankDrive(-power - powerAdjustment, -power + powerAdjustment);
      
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