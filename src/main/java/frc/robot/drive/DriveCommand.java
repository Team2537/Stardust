package frc.robot.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drive.DriveSubsystem.DriveMode;

public class DriveCommand extends CommandBase{
    public DriveCommand(){
        addRequirements(Robot.drivesys);
    }

    @Override
    public void initialize() {
        System.out.println("HIIIII");
    }

  // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(Robot.drivesys.getDriveMode() == DriveMode.kMecanum){
            Robot.drivesys.setMecanumDriveSpeed();
            System.out.println("HIIII");
        } else if (Robot.drivesys.getDriveMode() == DriveMode.kTank){
            Robot.drivesys.setTankDrive();
        } else {
            System.out.println("Ya done fucked up" + Robot.drivesys.getDriveMode());
        }
        Robot.drivesys.putEncodersToDash();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {
        Robot.drivesys.killDriveMotors();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}