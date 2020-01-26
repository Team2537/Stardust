package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class TestCommand extends CommandBase{
    public TestCommand(){
        addRequirements(Robot.drivesys);
    }

    @Override
    public void initialize() {
        System.out.println("testing");
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