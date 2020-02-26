package frc.robot.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.nav.MecanumDriveStraightCommand;

public class CenterPath extends SequentialCommandGroup {

    public CenterPath() {
        addCommands(
            new MecanumDriveStraightCommand(50, 0),
            new MecanumDriveStraightCommand(100, 90),
            new MecanumDriveStraightCommand(50, 0)
        );
    }




}