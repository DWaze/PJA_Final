package com.example.lhadj.pja_final;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/26/2016.
 */

public class SendingUDPDownload extends Thread {
    String fileName;
    String distIp;

    public SendingUDPDownload(String fileName, String distIp) {
        this.fileName = fileName;
        this.distIp = distIp;
    }

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
            // when the client click on an element to download the element is extracted with the file name and other details
            //and send a udp request to the server to get the file from a specified client
            DatagramSocket datagramSocket = new DatagramSocket();
            RequestDetail requestDetail = new RequestDetail("",new ArrayList<ListFile>(),"",distIp,1,fileName);
            byte[] b =serialize(requestDetail);
            DatagramPacket packet = new DatagramPacket(b,b.length, InetAddress.getByName("192.168.202.1"),9999);
            datagramSocket.send(packet);
            datagramSocket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
