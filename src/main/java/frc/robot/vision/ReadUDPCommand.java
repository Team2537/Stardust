/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ReadUDPCommand extends CommandBase {
  static long lastTimePrinted = 0;
  private DatagramSocket ds;
  private DatagramPacket DpReceive;
  private byte[] receive;

  /**
   * Creates a new ReadUDPCommand.
   */
  public ReadUDPCommand() {
    addRequirements(Robot.udpsys);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("At least we're running");
    try {
      ds = new DatagramSocket(2537);
    } catch (SocketException e) {
      e.printStackTrace();
    }

    receive = new byte[65535];
    DpReceive = null;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Create DatagramPacket to receive the data
    DpReceive = new DatagramPacket(receive, receive.length);

    // Receive the data in byte buffer
    try {
      ds.receive(DpReceive);
    } catch (IOException e) {
      e.printStackTrace();
    }

    

    Robot.udpsys.addToBuffer(receive);
    Target[] currentPacket = Robot.udpsys.getVisionPacket();
    
    if (System.currentTimeMillis() - lastTimePrinted >= 1000){
      SmartDashboard.putNumber("Number Of Targets", currentPacket.length);
      for (int i = 0; i < currentPacket.length; i++) {
          SmartDashboard.putNumber("CurrentTargetNumber", i);
          SmartDashboard.putNumber("Top Left X", currentPacket[i].getBoundingBox()[0].getX(CoordinateSystems.CARTESIAN_NORMALIZED));
          SmartDashboard.putNumber("Top Left Y", currentPacket[i].getBoundingBox()[0].getY(CoordinateSystems.CARTESIAN_NORMALIZED));
          SmartDashboard.putNumber("Bottom Right X", currentPacket[i].getBoundingBox()[1].getX(CoordinateSystems.CARTESIAN_NORMALIZED));
          SmartDashboard.putNumber("Bottom Right Y", currentPacket[i].getBoundingBox()[1].getY(CoordinateSystems.CARTESIAN_NORMALIZED));
          System.out.println("top left point: " + currentPacket[i].getBoundingBox()[0].getX(CoordinateSystems.CARTESIAN_NORMALIZED) + ","
                  + currentPacket[i].getBoundingBox()[0].getY(CoordinateSystems.CARTESIAN_NORMALIZED));
          System.out.println("bottom right point: " + currentPacket[i].getBoundingBox()[1].getX(CoordinateSystems.CARTESIAN_NORMALIZED) + ","
                  + currentPacket[i].getBoundingBox()[1].getY(CoordinateSystems.CARTESIAN_NORMALIZED));
      }
      lastTimePrinted = System.currentTimeMillis();
  }
 
  receive = new byte[65535];

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Premature ending. uh oh");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
