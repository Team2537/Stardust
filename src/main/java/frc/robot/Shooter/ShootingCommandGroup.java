/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// Connects the different steps in the shooting process into a loopable single command, starting the shooter, waiting until it's up to speed,
// feeding a ball to fire, and loading a new ball into position so the shooter is ready to loop again
public class ShootingCommandGroup extends SequentialCommandGroup {
  /**
   * The command is a singleton to allow the command to loop on a button without constantly restarting
   */
  private static ShootingCommandGroup instance = null;

  public ShootingCommandGroup() {

    super(new StartShooterCommand(), new ProperRPMCommand(), new FeedBallCommand(), new LoadBallCommand());
    
  }

  public static ShootingCommandGroup getInstance() {
    if (instance == null) {
      instance = new ShootingCommandGroup();
    }
    return instance;
  }
}
