package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RealignmentCommand extends CommandBase {
  /**
   * Creates a new SampleOpenCommand.
   */

    private double startingAngle;
    private double currentAngle;
    private static final double DEFAULT_PERCENT_OUTPUT = 1.00;
	private static final double MIN_PERCENT_OUTPUT = 0.90;
    private static final double ANGLE_kP = 1;
	private static final double TOLERANCE = 2; // degrees

  public RealignmentCommand() {
    addRequirements(Robot.drivesys);
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

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;                
  }
}