package frc.robot.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.maps.Left;
import frc.robot.maps.Middle;
import frc.robot.maps.Right;

public class AutoChooser extends SendableChooser<Command> {
	public AutoChooser() {
        setDefaultOption("Middle", new Middle());
        //addDefault("Middle", new Middle()); deprecated
        addOption("Left", new Left());
        //addObject("Left", new Left());
        addOption("Right", new Right());
		//addObject("Right", new RightGear());
		//addObject("Rotate Test", new RotateCommand(-60));
//		addObject("Vision test", new VisionRotate());
	}
}