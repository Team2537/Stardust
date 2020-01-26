package frc.robot.drive;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class TempDriveCommand extends CommandBase{
    public TempDriveCommand(){
        addRequirements(Robot.drivesys);
    }

    @Override
    public void initialize() {
    }

  // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.drivesys.setPeanutLeft(Robot.humanInput.xbox.getY(Hand.kLeft));
        Robot.drivesys.setPeanutRight(Robot.humanInput.xbox.getY(Hand.kRight));
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