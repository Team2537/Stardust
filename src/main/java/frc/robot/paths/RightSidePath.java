package frc.robot.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.nav.DriveStraightCommand;

public class RightSidePath extends SequentialCommandGroup {

    public RightSidePath() {
        addCommands(
            new DriveStraightCommand(100)
        );
    }




}