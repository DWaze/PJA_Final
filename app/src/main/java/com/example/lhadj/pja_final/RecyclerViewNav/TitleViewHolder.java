package com.example.lhadj.pja_final.RecyclerViewNav;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lhadj.pja_final.R;

/**
 * Created by lhadj on 12/25/2016.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {

    protected TextView title ;

    public TitleViewHolder(View view) {
        super(view);
        this.title = (TextView)view.findViewById(R.id.Client);
    }
}
