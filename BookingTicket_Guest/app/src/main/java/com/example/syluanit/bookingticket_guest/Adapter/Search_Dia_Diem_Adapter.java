package com.example.syluanit.bookingticket_guest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Interface.ItemClickListener;
import com.example.syluanit.bookingticket_guest.Model.DiaDiem;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class Search_Dia_Diem_Adapter extends RecyclerView.Adapter<Search_Dia_Diem_Adapter.ViewHolder> {

    Context context;
    ArrayList<DiaDiem> mangDiaDiem;

    public Search_Dia_Diem_Adapter(Context context, ArrayList<DiaDiem> mangDiaDiem) {
        this.context = context;
        this.mangDiaDiem = mangDiaDiem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dong_dia_diem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaDiem diaDiem = mangDiaDiem.get(position);
        holder.tvDiaDiem.setText(diaDiem.getPlace());
//        holder.ivIcon.setImageResource(diaDiem.getIcon());

    }

    @Override
    public int getItemCount() {
        return mangDiaDiem == null ? 0 : mangDiaDiem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvDiaDiem;
        public ImageView ivIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDiaDiem = itemView.findViewById(R.id.tv_diadiem);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(), false);
        }
    }
}
