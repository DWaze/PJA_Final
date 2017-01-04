package com.example.lhadj.pja_final;

import android.text.format.Formatter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/26/2016.
 */

public class GetListFiles {
    RequestDetail requestDetail ;

    ///// extractiing the list of files form the smart phone

    public GetListFiles() {
        requestDetail = new RequestDetail("Condore Client", new ArrayList<ListFile>(), Formatter.formatIpAddress(MainActivity.wm.getConnectionInfo().getIpAddress()),"",0,"");
    }

    public RequestDetail getFiles(){

        /// getting the list of files of the sharing directory

        File[] files = new File(MainActivity.directory).listFiles();

        int i = 0;
        for (File file : files) {
            //looping over each file and adding it to the list of files to return
            if (file.isFile()) {
                requestDetail.getFiles().add(new ListFile(file.getName(),""));
                i++;
            }
        }

        // returning the list of files
        return requestDetail;
    }

}
