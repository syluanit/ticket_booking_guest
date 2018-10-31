package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Adapter.Ticket_Info_Adapter;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketBookingInfo extends AppCompatActivity {

    RecyclerView rv_ticket;
    Ticket_Info_Adapter adapter;
    ArrayList<TicketInfo> ticketInfoArrayList;
    Button book;
    ImageView back;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking_info);

        rv_ticket = (RecyclerView) findViewById(R.id.rv_ticketInfo);
        book = (Button) findViewById(R.id.btn_bookticket);
        back = (ImageView) findViewById(R.id.ticket_back_pressed);
        rv_ticket.setHasFixedSize(true);
        ticketInfoArrayList = new ArrayList<>();

        adapter = new Ticket_Info_Adapter(this, ticketInfoArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_ticket.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rv_ticket.addItemDecoration(dividerItemDecoration);
        rv_ticket.setAdapter(adapter);
        prepareTicketInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) TicketBookingInfo.this).onBackPressed();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://192.168.43.218/busmanager/public/datveAndroid";
                sendBookingData(url);
            }
        });
    }

    private void sendBookingData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(TicketBookingInfo.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onResponse: " + response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("kq");
                            if (res.equals("success")) {
                                Dialog dialog = new Dialog(TicketBookingInfo.this);
                                dialog.setContentView(R.layout.dialog_no_seat_selected);
                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                TextView content = (TextView) dialog.findViewById(R.id.tv_content);
                                content.setText("Đặt vé thành công!");
                                TextView OK = (TextView) dialog.findViewById(R.id.tv_OK);
                                OK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(TicketBookingInfo.this, Home.class);
                                        startActivity(intent);
                                    }
                                });
                                dialog.show();
                            }
                            else if (res.equals("wrong")){
                                Toast.makeText(TicketBookingInfo.this, "Vé đã đặt", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TicketBookingInfo.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                database = new Database(getApplicationContext(), "ticket.sqlite", null, 1);

                Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");
                String userId = "";
                if (data.getCount() > 0){
                    Cursor currentUserDB = database.getDaTa("Select * from User");
                    while (currentUserDB.moveToNext()) {
                         userId = currentUserDB.getString(1);
                    }
                }
                params.put ("ID", Home.currentTicket.getId());
                params.put("MANG",Home.currentTicket.getSeatId());
                params.put ("MAKH", userId);
                params.put ("DODAI",String.valueOf(Home.currentTicket.getNumSeat()));

                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void prepareTicketInfo(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String user = bundle.getString("user","");
            String email = bundle.getString("email","");
            String phone = bundle.getString("phone","");
            ticketInfoArrayList.add(new TicketInfo("Họ và tên", user));
            ticketInfoArrayList.add(new TicketInfo("Email", email));
            ticketInfoArrayList.add(new TicketInfo("Điện thoại", phone));
        }
        ticketInfoArrayList.add(new TicketInfo("Tuyến đi",
                Home.currentTicket.getStartDestination() + " => "
                        + Home.currentTicket.getEndDestination()));
        ticketInfoArrayList.add(new TicketInfo("Ngày đi",Home.currentTicket.getDay()));
        ticketInfoArrayList.add(new TicketInfo("Giờ đi",Home.currentTicket.getTimeDep()));
//        String seat1 = "";
//        for (int i = 0; i < Home.currentTicket.getSeat().size(); i++) {
//            if (i != Home.currentTicket.getSeat().size() - 1) {
//                seat1 += (Home.currentTicket.getSeat().get(i).toString() + ", ");
//            } else seat1 += (Home.currentTicket.getSeat().get(i).toString() + ".");
//        }
        ticketInfoArrayList.add(new TicketInfo("Số lượng vé",
                String.valueOf(Home.currentTicket.getNumSeat())));
        ticketInfoArrayList.add(new TicketInfo("Vị trí", Home.currentTicket.getSeat()));

        ticketInfoArrayList.add(new TicketInfo("Tổng tiền",
                currencyFormat(
                String.valueOf(Integer.parseInt(Home.currentTicket.getPrice())
                * Home.currentTicket.getNumSeat() * 1000))));
        adapter.notifyDataSetChanged();
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}
