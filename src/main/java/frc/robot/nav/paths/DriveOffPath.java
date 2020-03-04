package frc.robot.nav.paths;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.nav.MecanumDriveStraightCommand;

public class DriveOffPath extends SequentialCommandGroup {

    public DriveOffPath() {
        addCommands(
            new LoadBallCommand(),
            new WaitCommand(0.5),
            new MecanumDriveStraightCommand(20, 0),
            new WaitCommand(.5),
            
        );



    }

}