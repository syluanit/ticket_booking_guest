package com.example.syluanit.bookingticket_guest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Activity.TimeListActivity;
import com.example.syluanit.bookingticket_guest.Model.TimeList;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;
import java.util.List;

public class TimeListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TimeList> timeList;

    public TimeListAdapter(TimeListActivity timeListActivity, int layout, ArrayList<TimeList> timeList) {
        this.timeList = timeList;
        this.layout = layout;
        this.context = timeListActivity;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout,null);

        // Ánh xạ
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//        ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

        // Gán giá trị
        TimeList time_List = timeList.get(position);

        tv_time.setText(time_List.getTime());
//        iv_icon.setImageResource(time_List.getIcon());

        return convertView;
    }
}
