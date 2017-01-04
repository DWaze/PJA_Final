package com.example.lhadj.pja_final;

import android.os.Handler;

import com.example.lhadj.pja_final.RecyclerViewFiles.FileRecyclerViewAdapter;
import com.example.lhadj.pja_final.RecyclerViewNav.RecyclerViewAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/26/2016.
 */

public class ProcessingUDPFiles extends Thread {
    ArrayList<RequestDetail> requestDetails;
    RecyclerViewAdapter recyclerViewAdapter;
    FileRecyclerViewAdapter recyclerViewAdapterFile;
    Handler hn;

    public ProcessingUDPFiles(RecyclerViewAdapter recyclerViewAdapter, FileRecyclerViewAdapter recyclerViewAdapterFile, Handler hn) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.recyclerViewAdapterFile = recyclerViewAdapterFile;
        this.hn = hn;
    }

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

    @Override
    public void run() {
        while (true){
            try {
                // getting the latest full list of files of all the users connected to the server in the port 4444
                DatagramSocket datagramSocket = new DatagramSocket(4444);
                byte[] buffer = new byte[1024*1024];
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                //replacing the current list of files with the ones received
                requestDetails = (ArrayList<RequestDetail>)deserialize(datagramPacket.getData());
                if(requestDetails.size()!=0){
                    MainActivity.requestDetails = (ArrayList<RequestDetail>)deserialize(datagramPacket.getData());
                    hn.post(new Runnable() {
                        @Override
                        public void run() {
                            //refreshing the layout
                            recyclerViewAdapterFile.loadNewData(requestDetails.get(MainActivity.clientPosition).getFiles());
                            recyclerViewAdapter.loadNewData(requestDetails);
                        }
                    });
                }
                datagramSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
