package com.example.lhadj.pja_final;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lhadj on 12/26/2016.
 */

public class ProcessingTCP extends Thread {

    Socket s ;
    String dowFile;


    public ProcessingTCP(Socket s, String dowFile) {
        this.s = s;
        this.dowFile = dowFile;
    }

    @Override
    public void run() {
        try {
            //reading the file bytes and writting it to the directorry
            BufferedInputStream in = new BufferedInputStream(s.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(dowFile)));
            while (true){
                int line = in.read();
                if(line<0){
                    break;
                }
                out.write(line);
            }
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
