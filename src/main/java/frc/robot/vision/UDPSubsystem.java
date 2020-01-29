/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class UDPSubsystem extends SubsystemBase {
  /**
   * Creates a new UDPSubsystem.
   */
  private static UDPSubsystem instance = null;
  ProtocolHandler protocolHandler;

  public static UDPSubsystem getInstance() {
    if (instance == null) {
        instance = new UDPSubsystem();
    }
    return instance;
  }

public Target[] getVisionPacket() {
  return VisionPacketHandler.decodeVisionPacket(protocolHandler.getLastString());
}

public Target[] getLastNonEmptyPacket() {
  return VisionPacketHandler.decodeVisionPacket(protocolHandler.getLastNonEmptyString());
}

public void addToBuffer(byte[] a){
  if (a.length > 1) {
      try {
          protocolHandler.addToBuffer(data(a).toString());
      } catch (Exception e){
          System.out.println("System Error");
          protocolHandler = new ProtocolHandler();
      }
  }
}

public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }


  @Override
  public void periodic() {
    protocolHandler = new ProtocolHandler();
    setDefaultCommand(new ReadUDPCommand());
  }
}
