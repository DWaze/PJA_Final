package com.example.lhadj.pja_final;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/26/2016.
 */

public class SendingUDPFR extends Thread {

    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }


    @Override
    public void run() {
        try {
            while (true){
                ///sending the query to the server to get the latest updatet list of file of all the clients connected to the network
                DatagramSocket datagramSocket = new DatagramSocket();

                RequestDetail requestDetail = new RequestDetail("",new ArrayList<ListFile>(),"","",2,"");

                byte[] b = serialize(requestDetail);

                DatagramPacket datagramPacket = new DatagramPacket(b,b.length, InetAddress.getByName("192.168.202.1"),9999);
                datagramSocket.send(datagramPacket);
                // resending every 4000 seconds
                sleep(4000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
