/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;




/**
 * Add your docs here.
 */
public class CANTalon extends TalonSRX{
    private ControlMode controlMode = ControlMode.PercentOutput;

    public CANTalon(int deviceNumber){
        super(deviceNumber);
    }

    public void setControlMode(ControlMode mode){
        this.controlMode = mode;
    }

    public void set(double demand){
        this.set(controlMode, demand);
    }
}
