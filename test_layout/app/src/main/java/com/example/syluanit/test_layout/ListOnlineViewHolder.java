package com.example.syluanit.test_layout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListOnlineViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_Email;
    public ListOnlineViewHolder(View itemView) {
        super(itemView);

        tv_Email = (TextView)itemView.findViewById(R.id.tv_email);
    }
}
