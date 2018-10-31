package com.example.syluanit.bookingticket_guest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Interface.ItemClickListener;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class So_Do_Giuong_Nam_Adapter_Scrolling extends RecyclerView.Adapter<So_Do_Giuong_Nam_Adapter_Scrolling.ViewHolder>{

    private Context context;
    ArrayList<GheNgoi> mangGheNgoi;

    public So_Do_Giuong_Nam_Adapter_Scrolling(Context context, ArrayList<GheNgoi> mangGheNgoi) {
        this.context = context;
        this.mangGheNgoi = mangGheNgoi;
    }

    @NonNull
    @Override
    public So_Do_Giuong_Nam_Adapter_Scrolling.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ghe_ngoi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final So_Do_Giuong_Nam_Adapter_Scrolling.ViewHolder holder, int position) {
        final GheNgoi gheNgoi = mangGheNgoi.get(position);
//        holder.iv_seat.setImageResource(gheNgoi.getHinhAnh());
        holder.setIsRecyclable(false);
        // TODO set seat' status
        // Invisible
        if (gheNgoi.getHinhAnh() == 0) {
            holder.iv_seat.setVisibility(View.GONE);
            holder.iv_seat.setEnabled(false);
            holder.iv_seat.setOnClickListener(null);
            holder.setItemClickListener(null);
            holder.tv_seat.setVisibility(View.GONE);
            holder.tv_seat.setEnabled(false);
            holder.tv_seat.setOnClickListener(null);
        } else {
            holder.iv_seat.setImageResource(gheNgoi.getHinhAnh());
            holder.tv_seat.setText(gheNgoi.getViTri());
        }
        // Available
        if (gheNgoi.getTrangThai() == 0){
            holder.iv_seat.setBackgroundColor(Color.TRANSPARENT);
        }
        else if (gheNgoi.getTrangThai() == 1){
            holder.iv_seat.setPressed(true);
        }
        else{
            holder.iv_seat.setEnabled(false);
            holder.iv_seat.setFocusable(false);
            holder.iv_seat.setOnClickListener(null);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                int seatStatus = gheNgoi.getTrangThai();
                if (seatStatus == 0){
                    So_Do_Cho_Ngoi_Activity.currentSeat.add(gheNgoi);
                    gheNgoi.setTrangThai(1) ;

                } else {
                    for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                        if (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri().equals(gheNgoi.getViTri())) {
                            So_Do_Cho_Ngoi_Activity.currentSeat.remove(i);
                            break;
                        }
                    }
                   gheNgoi.setTrangThai(0);
                }
                setSeatPositionText();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangGheNgoi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView iv_seat;
        public TextView tv_seat;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_seat = (ImageView) itemView.findViewById(R.id.seat);
            tv_seat = (TextView) itemView.findViewById(R.id.seatposition);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        //setter itemClickListener
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

    private void setSeatPositionText (){
        if (So_Do_Cho_Ngoi_Activity.currentSeat != null) {
            String seat = "";
            for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                if (i != So_Do_Cho_Ngoi_Activity.currentSeat.size() - 1) {
                    seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ", ");
                } else seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ".");
            }
            So_Do_Cho_Ngoi_Activity.tv_seatSelected.setText(seat);
        }
    }
}
