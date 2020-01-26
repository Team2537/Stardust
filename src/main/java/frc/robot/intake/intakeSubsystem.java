/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.intake;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intakeSubsystem extends SubsystemBase {
 
  private static intakeSubsystem instance = null;
  Solenoid solenoid1;
  /**
   * Creates a new intakeSubsystem.
   */
  private intakeSubsystem() {
     solenoid1 = new Solenoid(1);
 
  }
  
    public void OperatorControl() {
      
      }
      
    
    public static intakeSubsystem getInstance() {
      if (instance == null) {
          instance = new  intakeSubsystem();
      }
      return instance;
  }

  public void pneumaticExtend(){
    solenoid1.set(true);

  }
  public void pneumaticRetract(){
    solenoid1.set(false);
    
  
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
