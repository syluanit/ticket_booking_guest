package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.example.syluanit.bookingticket_guest.Adapter.HistoryAdapter;
import com.example.syluanit.bookingticket_guest.Adapter.RouteAdapter;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistory extends AppCompatActivity {

    ImageView back;
    ArrayList<CurrentTicket> historyArrayList;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    Database database;
    TextView noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        back = (ImageView) findViewById(R.id.back_pressed_history);
        noHistory = (TextView) findViewById(R.id.noHistory);

        database = new Database(this, "ticket.sqlite", null, 1);
        historyArrayList = new ArrayList<>();

        Cursor dataIsExist = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");

        if (dataIsExist.getCount() > 0) {
            Cursor data = database.getDaTa("Select * from Ticket");

            if (!(data.getCount() > 0)) {
                noHistory.setVisibility(View.VISIBLE);
            } else {
                noHistory.setVisibility(View.GONE);
                data.moveToLast();
               do {
                    historyArrayList.add(new CurrentTicket(String.valueOf(data.getInt(1)), data.getString(2),
                            data.getString(3), data.getString(4),
                            data.getString(5), data.getString(6), data.getString(7),
                            data.getString(8), data.getString(9),
                            data.getInt(10), data.getInt(11)));
                }  while (data.moveToPrevious());
            }
        }
        else noHistory.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.rv_HistoryBooking);
        recyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(this, historyArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(historyAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) BookingHistory.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
