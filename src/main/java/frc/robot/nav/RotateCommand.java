package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drive.DriveSubsystem.DriveMode;

//!!!!!!!!!!!!!!!!DO NOT TRY TO TURN 180 DEGREES!!!!!!!!!!!!!!!!!!!!!!

public class RotateCommand extends CommandBase {

  private double startingAngle;
  private double currentAngle;
  private double targetAngle;
  private double deltaAngle;
  private DriveMode mode;
  private static final double DEFAULT_PERCENT_OUTPUT = 0.25;
  private static final double MIN_PERCENT_OUTPUT = 0.1;
  private static final double ANGLE_kP = 1.30;
	private static final double TOLERANCE = 0.5; // degrees

  public RotateCommand(double angle, DriveMode mode) {
    addRequirements(Robot.drivesys);
    targetAngle = angle;
    this.mode = mode;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      Navx.getInstance().updateTotalAngle();
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

      if(mode == DriveMode.kMecanum){
        Robot.drivesys.setMecanumDriveSpeed(0, 0, -power * Math.signum(deltaAngle));
      } else if(mode == DriveMode.kTank){
        Robot.drivesys.setTankDrive(power * Math.signum(deltaAngle), -power * Math.signum(deltaAngle));
      }

      // Robot.drivesys.setPeanutLeft(power * Math.signum(deltaAngle));
      // Robot.drivesys.setPeanutRight(-power * Math.signum(deltaAngle));
      System.out.println(deltaAngle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
      Robot.drivesys.setTankDrive(0, 0);;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(deltaAngle) <= TOLERANCE);                
  }
}