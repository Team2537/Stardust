package frc.robot.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drive.DriveSubsystem.DriveMode;

public class SwitchDriveCommand extends CommandBase{
    public SwitchDriveCommand(){

    }

    @Override
    public void initialize() {
        switch(Robot.drivesys.getDriveMode()){
            case kMecanum:
                Robot.drivesys.setSolenoids(true);
                Robot.drivesys.setDriveMode(DriveMode.kTank);
                System.out.println("Its Tanky Time");
                break;
            case kTank:
                Robot.drivesys.setSolenoids(false);
                Robot.drivesys.setDriveMode(DriveMode.kMecanum);
                System.out.println("Its Swervy Time");
                break;
            default:
                System.out.println("Oops");
        }
    }

  // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
 
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