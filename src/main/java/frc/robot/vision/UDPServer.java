package frc.robot.vision;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread {
    private DatagramSocket recSocket;
    private boolean running;
    private byte[] buffer;

    public UDPServer() throws SocketException {
        recSocket = new DatagramSocket(2537);
    }

    public void run(){
        running = true;
        System.out.println("running");
        while(running){
            DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length);
            try {
                recSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            String received =
                    new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);

            if(received.equals("end")){
                running = false;
                continue;
            }


        }
    }
}
