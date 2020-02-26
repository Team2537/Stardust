package frc.robot.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.nav.MecanumDriveStraightCommand;

public class LeftSidePath extends SequentialCommandGroup {

    public LeftSidePath() {
        addCommands(
            new MecanumDriveStraightCommand(50, 0),
            new MecanumDriveStraightCommand(200, 90),
            new MecanumDriveStraightCommand(50, 0)
        );
    }




}