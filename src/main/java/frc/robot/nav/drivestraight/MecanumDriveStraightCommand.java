package frc.robot.nav.drivestraight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.nav.Navx;


public class MecanumDriveStraightCommand extends CommandBase {

  
  private double currentAngle;
  private double distance;
  private double remainingDistance;
  private double direction;
  private double xMultiplier, yMultiplier;
  private boolean vertical;
  private double aggregateError;
  
  private static final double DEFAULT_PERCENT_OUTPUT = 0.30;
  private static final double MIN_PERCENT_OUTPUT = 0.20;
  private static final double ANGLE_kP = 2.30;
  private static final double ANGLE_kI = 0.03;
  private static final double DISTANCE_kP = 1;
  private static final double SLOWING_ADJUSTMENT = 2;
  private static final double TOLERANCE = 0.3; // degrees
  private static final double DISTANCE_TOLERANCE = 2.50;
  private static final double SLOWING_DISTANCE = 10;
 


  public MecanumDriveStraightCommand(double targetDistance, double targetDirection) {
    addRequirements(Robot.drivesys);
    distance = targetDistance;
    direction = targetDirection;
    if (direction == 90 || direction == 270){
      vertical = false;
    } else {
      vertical = true;
    }
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
      remainingDistance = distance - Robot.drivesys.getEncoderDistance(vertical);
  

      if (remainingDistance <= SLOWING_DISTANCE) {
        power *= (remainingDistance + SLOWING_ADJUSTMENT) / (SLOWING_DISTANCE + SLOWING_ADJUSTMENT) * DISTANCE_kP;
      }
      
      
      double zRotationAdjustment = 0;

      if (Math.abs(currentAngle) >= TOLERANCE) {
          aggregateError += currentAngle;
          zRotationAdjustment = ((currentAngle/180 * ANGLE_kP) + (aggregateError/180 * ANGLE_kI)) * power;
          System.out.println("zrot" + zRotationAdjustment);
      } else {
        aggregateError = 0;
      }

      System.out.println("Encoders: " + Robot.drivesys.getEncoderAverage());
      
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
    return (Math.abs(Robot.drivesys.getEncoderDistance(vertical) - distance)) <= DISTANCE_TOLERANCE;
  }
}