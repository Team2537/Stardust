package frc.robot.maps;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Right extends CommandGroup {
	public Right(){
	
		addSequential(new WaitCommand(0.25));

	}
}