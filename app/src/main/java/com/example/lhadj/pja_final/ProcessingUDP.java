package com.example.lhadj.pja_final;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * Created by lhadj on 12/26/2016.
 */

public class ProcessingUDP extends Thread {
    DatagramPacket packet ;
    RequestDetail requestDetail;
    /// serialization and deserialisation process
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public ProcessingUDP(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            requestDetail=(RequestDetail)deserialize(packet.getData());
            switch (requestDetail.getQuery()){
                case 0:          /*Sending the latest list of files */
                    // caling the methode getListFiles to get the latest list of availebal files in of the client
                    GetListFiles getListFiles = new GetListFiles();
                    //putting the result in the request details object
                    RequestDetail requestDetail1 = getListFiles.getFiles();
                    DatagramSocket datagramSocket = new DatagramSocket();
                    byte[] bytes = serialize(requestDetail1);
                    DatagramPacket pack = new DatagramPacket(bytes,bytes.length,packet.getAddress(),9999);
                    datagramSocket.send(pack);
                    datagramSocket.close();
                    SendingUDPFR sendingUDPFR = new SendingUDPFR();
                    sendingUDPFR.start();
                    break;
                case 1:          /*Sending the file to the client requester */
                    Socket s = new Socket(packet.getAddress(),8888);
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(MainActivity.directory+File.separator+requestDetail.getFileToDownload())));
                    BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
                    while(true){
                        int line = in.read();
                        if(line<0)
                            break;
                        out.write(line);
                    }
                    out.close();
                    in.close();
                    s.close();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
