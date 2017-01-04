package com.example.lhadj.pja_final.RecyclerViewFiles;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lhadj.pja_final.ListFile;
import com.example.lhadj.pja_final.R;

import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */

public class FileRecyclerViewAdapter extends RecyclerView.Adapter<FileViewHolder>  {

    ArrayList<ListFile> files;
    int pos ;

    public FileRecyclerViewAdapter(ArrayList<ListFile> listFiles) {
        this.files = listFiles;
    }




    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfn,null);
        FileViewHolder fileViewHolder = new FileViewHolder(view);
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        ListFile titleItem = files.get(position);
        holder.filetitle.setText(titleItem.getFileName());
    }



    @Override
    public int getItemCount() {
        return (null != files ? files.size() : 0);
    }

    public void  loadNewData(ArrayList<ListFile> newRequestDetails){
        files = newRequestDetails;
        notifyDataSetChanged();
    }

    public ListFile getTitles(int position){
        return (null != files ? files.get(position) :null);
    }


}
