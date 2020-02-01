package frc.robot.nav;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestPath extends SequentialCommandGroup {

    public TestPath() {
        addCommands(
            new DriveStraightCommand(60),
            new RotateCommand(-90),
            new DriveStraightCommand(30),
            new RotateCommand(-90)
        );
    }

}