package com.example.lhadj.pja_final;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by lhadj on 12/26/2016.
 */

public class ListeningUDP extends Thread {

    ProcessingUDP processingUDP;
    DatagramSocket socket;
    @Override
    public void run() {

            try {
                socket = new DatagramSocket(9999);
                while (true){
                byte[] buffer = new byte[1024*1024];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                processingUDP = new ProcessingUDP(packet);
                processingUDP.start();
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
