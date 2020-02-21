package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RealignmentCommand extends CommandBase {
  // /**
  //  * Creates a new SampleOpenCommand.
  //  */

  //   private double startingAngle;
  //   private double currentAngle;
  //   private static final double DEFAULT_PERCENT_OUTPUT = 0.50;
	// private static final double MIN_PERCENT_OUTPUT = 0.40;
  //   private static final double ANGLE_kP = 1;
	// private static final double TOLERANCE = 2; // degrees

  // public RealignmentCommand() {
  //   addRequirements(Robot.drivesys);
  // }

  // // Called when the command is initially scheduled.
  // @Override
  // public void initialize() {
  //     startingAngle = Navx.getInstance().getYaw();
  //     currentAngle = Navx.getInstance().getYaw();
  // }

  // // Called every time the scheduler runs while the command is scheduled.
  // @Override
  // public void execute() {
  //     currentAngle = Navx.getInstance().getYaw();
  //     double power = DEFAULT_PERCENT_OUTPUT;
  //     power = Math.min(power, (Math.abs(currentAngle) / startingAngle) * power * ANGLE_kP) * Math.signum(currentAngle);
  //       power = Math.max(Math.abs(power), Math.abs(MIN_PERCENT_OUTPUT)) * Math.signum(power);
  //     Robot.drivesys.setPeanutLeft(-power * Math.signum(currentAngle));
  //     Robot.drivesys.setPeanutRight(power * Math.signum(currentAngle));
  //     System.out.println(currentAngle);
  // }

  // // Called once the command ends or is interrupted.
  // @Override
  // public void end(final boolean interrupted) {
  //     Robot.drivesys.setPeanutLeft(0);
  //     Robot.drivesys.setPeanutRight(0);
  // }

  // // Returns true when the command should end.
  // @Override
  // public boolean isFinished() {
  //   return (currentAngle <= TOLERANCE);                
  // }
}