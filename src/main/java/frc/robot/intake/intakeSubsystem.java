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

public class IntakeSubsystem extends SubsystemBase {
 
  private static IntakeSubsystem instance = null;
  private boolean engaged;
  private CANTalon flywheel1;
  //private CANTalon flywheel2;
  
  Solenoid solenoid1;
  Solenoid solenoid2;
  /**
   * Creates a new intakeSubsystem.
   */
  private IntakeSubsystem() {
     solenoid1 = new Solenoid(Ports.SOLENOID_PORT1); 
     solenoid2 = new Solenoid(Ports.SOLENOID_PORT2);
     flywheel1 = new CANTalon(Ports.FLYWHEEL_PORT);
     //flywheel2 = new CANTalon(2);
     engaged = false;
 
  }
  
    
  public void setSpeed(double speed){
    flywheel1.set(speed);
   
  }   

  public static IntakeSubsystem getInstance() {
    if (instance == null) {
          instance = new  IntakeSubsystem();
    }
    return instance;
  }

  public void setSolenoid(boolean set){
    solenoid1.set(set);
    solenoid2.set(set);
    setEngaged(set);
  }

  public boolean getSolenoid(){
    return solenoid1.get();
  }

  public boolean getEngaged(){
    return engaged;
  }

  /**
   * @param engaged the engaged to set
   */
  public void setEngaged(boolean engaged) {
    this.engaged = engaged;
  }




  @Override
  public void periodic() {
    setDefaultCommand(new IntakeMotorsCommand());
  }
}
