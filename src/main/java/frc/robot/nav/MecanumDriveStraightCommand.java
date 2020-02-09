package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class MecanumDriveStraightCommand extends CommandBase {

  
  private double currentAngle;
  private double distance;
  private double remainingDistance;
  private String direction;
  private double xMultiplier, yMultiplier;
  
  private static final double DEFAULT_PERCENT_OUTPUT = 0.30;
  private static final double MIN_PERCENT_OUTPUT = 0.20;
  private static final double ANGLE_kP = 0.25;
  private static final double TOLERANCE = 2; // degrees
  private static final double DISTANCE_TOLERANCE = 2;
  private static final double SLOWING_DISTANCE = 20;


  public MecanumDriveStraightCommand(double targetDistance, String targetDirection) {
    addRequirements(Robot.drivesys);
    distance = targetDistance;
    direction = targetDirection;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      Navx.getInstance().reset();
      Navx.getInstance().reset();
      Robot.drivesys.resetPeanutEnoders();
      currentAngle = Navx.getInstance().getYaw();
      if (direction == "X") {
        xMultiplier = 1;
        yMultiplier = 0;
      } else if (direction == "Y") {
        xMultiplier = 0;
        yMultiplier = 1;
      }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      double power = DEFAULT_PERCENT_OUTPUT * Math.signum(distance);
      
      
      
      double zRotationAdjustment = 0;

      if (Math.abs(currentAngle) <= TOLERANCE) {
          zRotationAdjustment = currentAngle/180 * ANGLE_kP * power;
      }
      //mecanum drive (power*yMultiplier, power*xMultiplier, zrotationadjustment)
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.killPeanutMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}