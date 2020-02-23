/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootingCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new CommandGroup.
   */
  private static ShootingCommandGroup instance = null;

  private ShootingCommandGroup() {
    super(new StartShooterCommand(), new LoadBallCommand(), new ProperRPMCommand(), new FeedBallCommand(), new LoadBallCommand());

    //super(new TestCommmand("Hi"), new WaitCommand(1), new TestCommmand("Bye"));
    
  }

  public static ShootingCommandGroup getInstance() {
    if (instance == null) {
      instance = new ShootingCommandGroup();
    }
    return instance;
  }
}
