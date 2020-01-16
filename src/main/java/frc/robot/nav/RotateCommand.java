package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RotateCommand extends CommandBase {
  /**
   * Creates a new SampleOpenCommand.
   */

    private double startingAngle;
    private double currentAngle;
    private double targetAngle;
    private double deltaAngle;
    private static final double DEFAULT_PERCENT_OUTPUT = 1.00;
	private static final double MIN_PERCENT_OUTPUT = 0.90;
    private static final double ANGLE_kP = 1;
	private static final double TOLERANCE = 2; // degrees

  public RotateCommand(double angle) {
    addRequirements(Robot.drivesys);
    targetAngle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      startingAngle = Navx.getInstance().getYaw();
      currentAngle = Navx.getInstance().getYaw();
      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      currentAngle = Navx.getInstance().getYaw();
      deltaAngle = (currentAngle - startingAngle);
      double power = DEFAULT_PERCENT_OUTPUT;
      power = Math.min(power, (Math.abs(deltaAngle) / 1800)+.9) * Math.signum(deltaAngle);
          power = Math.max(power, MIN_PERCENT_OUTPUT);
      Robot.drivesys.setPeanutLeft(-power);
      Robot.drivesys.setPeanutRight(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.setPeanutLeft(0);
      Robot.drivesys.setPeanutRight(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (deltaAngle <= TOLERANCE);                
  }
}