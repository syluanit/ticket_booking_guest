package com.example.syluanit.bookingticket_guest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Activity.Home;
import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class TravelHistoryAdapter extends RecyclerView.Adapter<TravelHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CurrentTicket> routeArrayList;

    public TravelHistoryAdapter(Context context, ArrayList<CurrentTicket> routeArrayList) {
        this.context = context;
        this.routeArrayList = routeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.travel_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CurrentTicket route = routeArrayList.get(position);
        holder.tv_price.setText(route.getPrice());
        holder.tv_timeArr.setText(route.getTimeArr());
        holder.tv_timeDep.setText(route.getTimeDep());
        holder.tv_date.setText(route.getDay());
        holder.tv_startPoint.setText(route.getStartDestination());
        holder.tv_endPoint.setText(route.getEndDestination());
//        holder.tv_seat.setText(route.getSeat().substring(0, route.getSeat().length()-2));
        holder.tv_seat.setText(route.getSeat());
        holder.tv_typeSeat.setText(route.getTypeSeat() == 1 ? "Ghế Ngồi" : "Giường Nằm");

    }

    @Override
    public int getItemCount() {
        return routeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_price, tv_timeDep, tv_timeArr, tv_startPoint, tv_endPoint, tv_date, tv_seat, tv_typeSeat;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_price = (TextView) itemView.findViewById(R.id.tv_priceTicketHistory);
            tv_timeDep = (TextView) itemView.findViewById(R.id.tv_timeDepHistory);
            tv_timeArr = (TextView) itemView.findViewById(R.id.tv_timeArrHistory);
            tv_endPoint = (TextView) itemView.findViewById(R.id.tv_endPoint);
            tv_date = (TextView) itemView.findViewById(R.id.tv_dateHistory);
            tv_startPoint = (TextView) itemView.findViewById(R.id.tv_startPoint);
            tv_seat = (TextView) itemView.findViewById(R.id.tv_bookingseat);
            tv_typeSeat = (TextView) itemView.findViewById(R.id.tv_typeSeat);
        }
    }
}
