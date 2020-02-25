package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class MecanumDriveStraightCommand extends CommandBase {

  
  private double currentAngle;
  private double distance;
  private double remainingDistance;
  private double direction;
  private double xMultiplier, yMultiplier;
  
  private static final double DEFAULT_PERCENT_OUTPUT = 0.30;
  private static final double MIN_PERCENT_OUTPUT = 0.20;
  private static final double ANGLE_kP = 0.05;
  private static final double DISTANCE_kP = 1;
  private static final double SLOWING_ADJUSTMENT = 5;
  private static final double TOLERANCE = 2; // degrees
  private static final double DISTANCE_TOLERANCE = 2;
  private static final double SLOWING_DISTANCE = 5;


  public MecanumDriveStraightCommand(double targetDistance, double targetDirection) {
    addRequirements(Robot.drivesys);
    distance = targetDistance;
    direction = targetDirection;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      // Navx.getInstance().updateTotalAngle();
      Navx.getInstance().reset();
      Navx.getInstance().reset();
      Robot.drivesys.resetEncoders();

      currentAngle = Navx.getInstance().getYaw();

      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      double power = DEFAULT_PERCENT_OUTPUT * Math.signum(distance);
      remainingDistance = distance - Robot.drivesys.getEncoderDistance();

      if (remainingDistance <= SLOWING_DISTANCE) {
        power *= (remainingDistance + SLOWING_ADJUSTMENT) / (SLOWING_DISTANCE + SLOWING_ADJUSTMENT) * DISTANCE_kP;
      }
      
      
      double zRotationAdjustment = 0;

      if (Math.abs(currentAngle) <= TOLERANCE) {
          zRotationAdjustment = currentAngle/180 * ANGLE_kP * power;
      }

      System.out.println("Encoders: " + Robot.drivesys.getEncoderDistance());
      
      Robot.drivesys.setPolarDriveSpeed(power, direction, zRotationAdjustment);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.setTankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.drivesys.getEncoderDistance() >= distance;
  }
}