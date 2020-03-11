package frc.robot.nav.paths;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.nav.drivestraight.MecanumDriveStraightCommand;

public class DirectlyInFrontPath extends SequentialCommandGroup {

    public DirectlyInFrontPath() {
        addCommands(
            new LoadBallCommand(),
            new MecanumDriveStraightCommand(110, 180),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new StopShooterCommand()
        );



    }

}