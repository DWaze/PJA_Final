package com.example.lhadj.pja_final;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lhadj on 12/26/2016.
 */

public class ListeningTCP extends Thread {

    ServerSocket socket ;


    @Override
    public void run() {
        try {
            socket = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                ///// listening to the tcp requests
                Socket s = socket.accept();
                /// calling the method preccessing TCP to process the file transmition
                ProcessingTCP processingTCP= new ProcessingTCP(s,MainActivity.directory+ File.separator+MainActivity.fileDownloadQue.get(0));
                /// removing the filedownload que from the list of file to download in tha same time
                MainActivity.fileDownloadQue.remove(0);
                processingTCP.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
