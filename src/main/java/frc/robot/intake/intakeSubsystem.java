/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.intake;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.input.Ports;
import frc.robot.util.CANTalon;

public class intakeSubsystem extends SubsystemBase {
 
  private static intakeSubsystem instance = null;
  private CANTalon flywheel1;
  private CANTalon flywheel2;
  
  Solenoid solenoid1;
  Solenoid solenoid2;
  /**
   * Creates a new intakeSubsystem.
   */
  private intakeSubsystem() {
     solenoid1 = new Solenoid(6, Ports.SOLENOID_PORT1);
     //solenoid2 = new Solenoid(Ports.SOLENOID_PORT2);
     flywheel1 = new CANTalon(Ports.FLYWHEEL_PORT);
     //flywheel2 = new CANTalon(2);
 
  }
  
    
      public void setSpeed(double speed){
        flywheel1.set(speed);
       
      }

      public void setOtherSpeed(double speed){
        flywheel2.set(speed);
       
      }
      
    public static intakeSubsystem getInstance() {
      if (instance == null) {
          instance = new  intakeSubsystem();
      }
      return instance;
  }

  public void pneumaticExtend(){
    System.out.println("A BEFORE");
    solenoid1.set(true);
    System.out.println("A AFTER");

  }

  public void setSolenoid(boolean set){
    solenoid1.set(set);
    System.out.println("SET SOLENOID");
  }

  public void pneumaticRetract(){
    solenoid1.set(false);
    System.out.println("Pressed B");
    
  
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
