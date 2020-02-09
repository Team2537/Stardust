package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

//!!!!!!!!!!!!!!!!DO NOT TRY TO TURN 180 DEGREES!!!!!!!!!!!!!!!!!!!!!!

public class RotateCommand extends CommandBase {

  private double startingAngle;
  private double currentAngle;
  private double targetAngle;
  private double deltaAngle;
  private static final double DEFAULT_PERCENT_OUTPUT = 0.50;
  private static final double MIN_PERCENT_OUTPUT = 0.30;
  private static final double ANGLE_kP = 1.30;
	private static final double TOLERANCE = 1.50; // degrees

  public RotateCommand(double angle) {
    addRequirements(Robot.drivesys);
    targetAngle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      Navx.getInstance().reset();
      Navx.getInstance().reset();
      startingAngle = Navx.getInstance().getYaw();
      currentAngle = Navx.getInstance().getYaw();
      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      deltaAngle = (targetAngle - currentAngle);
      double power = DEFAULT_PERCENT_OUTPUT;
      power = Math.min(Math.abs(power), (Math.abs(deltaAngle) / (targetAngle - startingAngle) * power * ANGLE_kP)) * Math.signum(deltaAngle);
        power = Math.max(Math.abs(power), Math.abs(MIN_PERCENT_OUTPUT)) * Math.signum(power);
      
      Robot.drivesys.setTankDrive(power * Math.signum(deltaAngle),
                                 -power * Math.signum(deltaAngle));
      
      System.out.println(deltaAngle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.killDriveMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(deltaAngle) <= TOLERANCE);                
  }
}