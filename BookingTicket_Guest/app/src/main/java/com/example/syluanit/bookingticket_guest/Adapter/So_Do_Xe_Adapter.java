package com.example.syluanit.bookingticket_guest.Adapter;

//import android.content.Context;
//import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
//import com.example.syluanit.bookingticket_guest.R;
//
//import java.util.ArrayList;
//
//public class So_Do_Xe_Adapter extends RecyclerView.Adapter<So_Do_Xe_Adapter.ViewHolder>  {
//
//    ArrayList<GheNgoi> gheNgoi;
//    Context context;
//
//    public So_Do_Xe_Adapter(ArrayList<GheNgoi> gheNgoi, Context context) {
//        this.gheNgoi = gheNgoi;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.giuong_nam_, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        GheNgoi gheNgoi = gheNgoi.get(position);
//        if (gheNgoi.getHinhAnh() == 0) {
//            holder.imgSeat.setVisibility(View.GONE);
//            holder.imgSeat.setEnabled(false);
//            holder.imgSeat.setOnClickListener(null);
//            holder.seatposition.setVisibility(View.GONE);
//            holder.seatposition.setEnabled(false);
//            holder.seatposition.setOnClickListener(null);
//        }
//        else {
//            holder.imgSeat.setImageResource(gheNgoi.getHinhAnh());
//            holder.seatposition.setText( gheNgoi.getViTri());
//        }
//
//        if (gheNgoi.getCua() == 1){
//            holder.tvDoor.setVisibility(View.VISIBLE);
//        }
//
//        if (gheNgoi.getTrangThai() == 0){
//            holder.imgSeat.setBackgroundColor(Color.TRANSPARENT);
//        }
//        else if (gheNgoi.getTrangThai() == 1){
//            holder.imgSeat.setPressed(true);
//            //            holder.imgSeat.setBackgroundColor(Color.RED);
//        }
//        else{
//            holder.imgSeat.setEnabled(false);
//            holder.imgSeat.setFocusable(false);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return gheNgoi.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgSeat;
//        TextView tvDoor, seatposition;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            imgSeat = (ImageView) itemView.findViewById(R.id.seat);
//            seatposition = (TextView) itemView.findViewById(R.id.seatposition);
//            tvDoor = (TextView) itemView.findViewById(R.id.tvDoor);
//
//        }
//    }
//}

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import java.util.List;

public class So_Do_Xe_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GheNgoi> gheNgoiList;

    public So_Do_Xe_Adapter(Context context, int layout, List<GheNgoi> gheNgoiList) {
        this.context = context;
        this.layout = layout;
        this.gheNgoiList = gheNgoiList;
    }

    @Override
    public int getCount() {
        return gheNgoiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgSeat;
        TextView seatposition, tvDoor;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.imgSeat = (ImageView) convertView.findViewById(R.id.seat);
            holder.seatposition = (TextView) convertView.findViewById(R.id.seatposition);
            holder.tvDoor = (TextView) convertView.findViewById(R.id.tvDoor);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GheNgoi gheNgoi = gheNgoiList.get(position);

        if (gheNgoi.getHinhAnh() == 0) {
            holder.imgSeat.setVisibility(View.GONE);
            holder.imgSeat.setEnabled(false);
            holder.imgSeat.setOnClickListener(null);
            holder.seatposition.setVisibility(View.GONE);
            holder.seatposition.setEnabled(false);
            holder.seatposition.setOnClickListener(null);
        }
        else {
            holder.imgSeat.setImageResource(gheNgoi.getHinhAnh());
            holder.seatposition.setText( gheNgoi.getViTri());
        }
        if (position == 4){
            holder.tvDoor.setVisibility(View.VISIBLE);
        }

        if (gheNgoi.getTrangThai() == 0){
            holder.imgSeat.setBackgroundColor(Color.TRANSPARENT);
        }
        else if (gheNgoi.getTrangThai() == 1){
            holder.imgSeat.setPressed(true);
            //            holder.imgSeat.setBackgroundColor(Color.RED);
        }
        else{
            holder.imgSeat.setEnabled(false);
            holder.imgSeat.setFocusable(false);
        }
        return convertView;
    }
}