package frc.robot.nav;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.robot.nav.paths.BotInMiddlePath;
import frc.robot.nav.paths.BotOnLeftPath;
import frc.robot.nav.paths.DirectlyInFrontPath;
import frc.robot.nav.paths.DriveOffPath;
import frc.robot.nav.paths.InFrontAndSkrt;
import frc.robot.nav.paths.InFrontGetTrench;

public class AutoChooser {

    private SendableChooser<CommandGroupBase> startingPosition;

    public static AutoChooser instance;

    public AutoChooser() {
        startingPosition = new SendableChooser<>();
        startingPosition.setDefaultOption("Drive Off Line", new DriveOffPath());
        startingPosition.addOption("Directly in Front", new DirectlyInFrontPath());
        startingPosition.addOption("In the Middle", new BotInMiddlePath());
        startingPosition.addOption("On the Left", new BotOnLeftPath());
        startingPosition.addOption("That shooty trenchy boi", new InFrontGetTrench());
        startingPosition.addOption("In Front and Skrt", new InFrontAndSkrt());
        SmartDashboard.putData("AutoChoose", startingPosition);
    }

    public static AutoChooser getInstance() {
        if (instance == null) {
            instance = new AutoChooser();
        }
        return instance;
    }

    public CommandGroupBase getPath() {
        CommandGroupBase chosenPath = startingPosition.getSelected();        
        return chosenPath;
    }
}