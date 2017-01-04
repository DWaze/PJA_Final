package com.example.lhadj.pja_final.RecyclerViewNav;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lhadj.pja_final.R;
import com.example.lhadj.pja_final.RequestDetail;

import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<TitleViewHolder> {

    ArrayList<RequestDetail> requestDetails;

    public RecyclerViewAdapter(ArrayList<RequestDetail> titles) {
        this.requestDetails = titles;
    }




    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
        TitleViewHolder titleViewHolder = new TitleViewHolder(view);
        return titleViewHolder;
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position) {
        RequestDetail titleItem = requestDetails.get(position);
        holder.title.setText(titleItem.getClientName());
    }

    @Override
    public int getItemCount() {
        return (null != requestDetails ? requestDetails.size() : 0);
    }

    public void  loadNewData(ArrayList<RequestDetail> newRequestDetails){
        requestDetails = newRequestDetails;
        notifyDataSetChanged();
    }

    public RequestDetail getTitles(int position){
        return (null != requestDetails ? requestDetails.get(position) :null);
    }


}
