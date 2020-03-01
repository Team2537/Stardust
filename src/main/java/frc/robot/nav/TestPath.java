package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;

public class TestPath extends SequentialCommandGroup {

    public TestPath() {
        addCommands(
            new LoadBallCommand(),
            new MecanumDriveStraightCommand(75, 180),
            new WaitCommand(.5),
            new MecanumDriveStraightCommand(62, 270),
            new MecanumDriveStraightCommand(30, 180),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new StopShooterCommand()
        );



    }

}