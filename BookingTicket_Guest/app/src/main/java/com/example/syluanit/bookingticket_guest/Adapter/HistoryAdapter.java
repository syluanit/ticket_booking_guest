package com.example.syluanit.bookingticket_guest.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Activity.Home;
import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CurrentTicket> routeArrayList;
    public static int checkHistory = 0;
    String url;

    public HistoryAdapter(Context context, ArrayList<CurrentTicket> routeArrayList) {
        this.context = context;
        this.routeArrayList = routeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CurrentTicket route = routeArrayList.get(position);
        holder.tv_price.setText(currencyFormat(
                String.valueOf(Integer.parseInt(route.getPrice()) * 1000)));
        holder.tv_timeArr.setText(route.getTimeArr());
        holder.tv_timeDep.setText(route.getTimeDep());
        holder.tv_date.setText(route.getDay());
        holder.tv_startPoint.setText(route.getStartDestination());
        holder.tv_endPoint.setText(route.getEndDestination());
        holder.tv_seat.setText(route.getSeat().substring(0,route.getSeat().length()-1));
        holder.tv_typeSeat.setText(route.getTypeSeat() == 1 ? "Giường Nằm" : "Ghế Ngồi"  );

        holder.btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Server Request
                // TODO SEARCH history
                String source = route.getDay();

                String[] sourceSplit= source.split("-");
                // get date month year phrom route
                int anno= Integer.parseInt(sourceSplit[2]);
                int mese= Integer.parseInt(sourceSplit[1]);
                int giorno= Integer.parseInt(sourceSplit[0]);

                String time = route.getTimeDep();
                String[] timeArray = time.split(":");
                int hour = Integer.parseInt(timeArray[0]);
                int minute = Integer.parseInt(timeArray[1]);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.set(anno,mese-1,giorno, hour,minute);
                Date   data1 = calendar.getTime();
                //phlag
                int outdated = 0;
                // TODO compare current time with ticket time
                if (System.currentTimeMillis() > data1.getTime()) {
                    outdated = 1;
                }
                if (outdated == 1) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_outday);
                    TextView tv_annoucement = dialog.findViewById(R.id.tv_OK_outday);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tv_annoucement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                } else {

                    String ip = context.getResources().getString(R.string.ip);
                    String address = context.getResources().getString(R.string.address);
                    url = ip + address + "/chonveAndroid";
//                    final String url = "http://192.168.43.218/busmanager/public/chonveAndroid";

                    final RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("AAA", "onResponse: " + response.toString());

                                        Home.currentTicket.setStartDestination(route.getStartDestination());
                                        Home.currentTicket.setEndDestination(route.getEndDestination());
                                        Home.currentTicket.setDay(route.getDay());
                                        Home.currentTicket.setTimeArr(route.getTimeArr());
                                        Home.currentTicket.setTypeSeat(route.getTypeSeat());
//                                        Home.currentTicket.setSeat(route.getSeat());
                                        Home.currentTicket.setTimeDep(route.getTimeDep());
                                        Home.currentTicket.setPrice(currencyFormat(route.getPrice()));
                                        Home.currentTicket.setId(route.getId());
//                                        Home.currentTicket.setSeatId(route.getSeatId());

                                        checkHistory = 1;

                                        Intent i = new Intent(context, So_Do_Cho_Ngoi_Activity.class);
                                        i.putExtra("ticketMap", response);
                                        context.startActivity(i);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng hoặc thử lại", Toast.LENGTH_SHORT).show();
                                    Log.d("AAA", "onErrorResponse: " + error.toString());
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("ID", route.getId());
                            Log.d("AAA", "getParams: OK!!!");
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return routeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_price, tv_timeDep, tv_timeArr, tv_startPoint, tv_endPoint, tv_date, tv_seat,tv_typeSeat;
        public Button btn_history;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_price = (TextView) itemView.findViewById(R.id.tv_priceTicketHistory);
            tv_timeDep = (TextView) itemView.findViewById(R.id.tv_timeDepHistory);
            tv_timeArr = (TextView) itemView.findViewById(R.id.tv_timeArrHistory);
            tv_endPoint = (TextView) itemView.findViewById(R.id.tv_endPoint);
            tv_date = (TextView) itemView.findViewById(R.id.tv_dateHistory);
            tv_startPoint = (TextView) itemView.findViewById(R.id.tv_startPoint);
            btn_history = (Button) itemView.findViewById(R.id.btnHistory);
            tv_seat = (TextView) itemView.findViewById(R.id.tv_bookingseat);
            tv_typeSeat = (TextView) itemView.findViewById(R.id.tv_typeSeat);

        }
    }
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}
