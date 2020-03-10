package frc.robot.nav.paths;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.nav.MecanumDriveStraightCommand;

public class InFrontAndSkrt extends SequentialCommandGroup {

    public InFrontAndSkrt() {
        addCommands(
            new LoadBallCommand(),
            new MecanumDriveStraightCommand(110, 180),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new StopShooterCommand(),
            new MecanumDriveStraightCommand(60, 270)
        );



    }

}