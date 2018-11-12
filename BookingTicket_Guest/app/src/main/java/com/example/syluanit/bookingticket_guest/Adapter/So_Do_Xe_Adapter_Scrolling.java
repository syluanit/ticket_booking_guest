package com.example.syluanit.bookingticket_guest.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Interface.ItemClickListener;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class So_Do_Xe_Adapter_Scrolling extends RecyclerView.Adapter<So_Do_Xe_Adapter_Scrolling.ViewHolder>{

    private Context context;
    ArrayList<GheNgoi> mangGheNgoi;
    String url = "http://192.168.43.218/busmanager/public/xulydatveAndroid";
    String url1 = "http://192.168.43.218/busmanager/public/destroydatveAndroid";

    Database database;
    public static String waitingSeat = "";

    public So_Do_Xe_Adapter_Scrolling(Context context, ArrayList<GheNgoi> mangGheNgoi) {
        this.context = context;
        this.mangGheNgoi = mangGheNgoi;
    }

    @NonNull
    @Override
    public So_Do_Xe_Adapter_Scrolling.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ghe_ngoi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final So_Do_Xe_Adapter_Scrolling.ViewHolder holder, int position) {
        final GheNgoi gheNgoi = mangGheNgoi.get(position);
//        holder.iv_seat.setImageResource(gheNgoi.getHinhAnh());
        holder.setIsRecyclable(false);
        // TODO set seat' status
        // Invisible vị trí trống đương đi
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
        }//click chọn
        else if (gheNgoi.getTrangThai() == 1){
            holder.iv_seat.setPressed(true);
        }
        else{//đã đặt
            holder.iv_seat.setEnabled(false);
            holder.iv_seat.setFocusable(false);
            holder.iv_seat.setOnClickListener(null);
        }
        //click  on item
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                int seatStatus = gheNgoi.getTrangThai();
                if (seatStatus == 0){
                    So_Do_Cho_Ngoi_Activity.currentSeat.add(gheNgoi);
                    waitingSeat += gheNgoi.getViTri() +",";
                    gheNgoi.setTrangThai(1) ;
                    sendData(url, gheNgoi.getId(), position);
                } else {

                    sendDataDestroy(url1, gheNgoi.getId() ,position);
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

    private void sendData(String url, final String ticketId, final int position){

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: yeahyeah" + response.toString());
//
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("kq");
                            if( result.equals("0")){

                                for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                                    if (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri().equals(mangGheNgoi.get(position).getViTri())) {
                                        So_Do_Cho_Ngoi_Activity.currentSeat.remove(i);
                                        break;
                                    }
                                }
                                mangGheNgoi.get(position).setTrangThai(0);
                                setSeatPositionText();
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Vui lòng kiểm tra kết nối sau đó thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
//                        mangGheNgoi.get(position).setTrangThai(0);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                database = new Database(context, "ticket.sqlite", null, 1);
                String userId = "";
                Cursor currentUserDB = database.getDaTa("Select * from User");
                while (currentUserDB.moveToNext()) {
                    userId = currentUserDB.getString(1);
                }

                params.put("MA", ticketId);
                params.put("MAKH", userId);

                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };

        int socketTimeout = 300000;//300 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    private void sendDataDestroy(String url, final String ticketId, final int position){

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: yeahyeah" + response.toString());
//
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("kq");
                            if( result.equals("1")){

                                for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                                    if (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri().equals(mangGheNgoi.get(position).getViTri())) {
                                        So_Do_Cho_Ngoi_Activity.currentSeat.remove(i);
                                        break;
                                    }
                                }
                                mangGheNgoi.get(position).setTrangThai(0);
                                setSeatPositionText();
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Vui lòng kiểm tra kết nối sau đó thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MA", ticketId);
                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}

