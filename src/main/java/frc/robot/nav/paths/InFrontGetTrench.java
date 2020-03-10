package frc.robot.nav.paths;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shooter.LoadBallCommand;
import frc.robot.Shooter.ShootingCommandGroup;
import frc.robot.Shooter.StopShooterCommand;
import frc.robot.drive.DriveSubsystem.DriveMode;
import frc.robot.intake.IntakeCommandGroup;
import frc.robot.intake.IntakeMotorCommand;
import frc.robot.intake.MoveIntakeCommand;
import frc.robot.nav.ExtraMecanumDriveStraightCommand;
import frc.robot.nav.MecanumDriveStraightCommand;
import frc.robot.nav.RotateCommand;

public class InFrontGetTrench extends SequentialCommandGroup {

    public InFrontGetTrench() {
        addCommands(
            new LoadBallCommand(),
            new MecanumDriveStraightCommand(110, 180),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new ShootingCommandGroup(),
            new StopShooterCommand(),
            new MecanumDriveStraightCommand(90, 0),
            new MecanumDriveStraightCommand(44, 270),
            new RotateCommand(90, DriveMode.kMecanum),
            new RotateCommand(90, DriveMode.kMecanum),
            new IntakeCommandGroup()
        );



    }

}