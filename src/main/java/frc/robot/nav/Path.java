package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Path extends SequentialCommandGroup {

    public Path() {
        addCommands(
            new MecanumDriveStraightCommand(30, "X")
        );
    }

}