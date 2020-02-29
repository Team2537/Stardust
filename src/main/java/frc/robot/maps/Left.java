package frc.robot.maps;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.nav.MecanumDriveStraightCommand;

public class Left extends CommandGroup {
	public Left() {
        //addSequential(new MecanumDriveStraightCommand(0.25, 4));
        addSequential(new WaitCommand(0.25));

	}
}