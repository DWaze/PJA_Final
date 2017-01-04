package com.example.lhadj.pja_final.RecyclerViewFiles;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lhadj.pja_final.R;
import com.example.lhadj.pja_final.SendingUDPDownload;

import static com.example.lhadj.pja_final.MainActivity.clientPosition;
import static com.example.lhadj.pja_final.MainActivity.fileDownloadQue;
import static com.example.lhadj.pja_final.MainActivity.fileToDow;
import static com.example.lhadj.pja_final.MainActivity.requestDetails;

/**
 * Created by lhadj on 12/25/2016.
 */

public class FileViewHolder extends RecyclerView.ViewHolder {

    protected TextView filetitle;
    int pos;
    Button downloadBtn;
    Button deleteButton ;

    public FileViewHolder(final View view) {
        super(view);
        this.filetitle = (TextView)view.findViewById(R.id.fileNm);
        downloadBtn = (Button)view.findViewById(R.id.Download);
        deleteButton = (Button)view.findViewById(R.id.Delete);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = getAdapterPosition();
                fileToDow = requestDetails.get(clientPosition).getFiles().get(pos);
                fileDownloadQue.add(fileToDow.getFileName());
                SendingUDPDownload sendingUDPDownload = new SendingUDPDownload(requestDetails.get(clientPosition).getFiles().get(pos).getFileName(),requestDetails.get(clientPosition).getOwnerIp());
                sendingUDPDownload.start();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
