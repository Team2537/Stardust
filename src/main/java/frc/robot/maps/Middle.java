package frc.robot.maps;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Middle extends CommandGroup {
	public Middle() {
	
		addSequential(new WaitCommand(0.25));

	}
}