package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.Adapter.HistoryAdapter;
import com.example.syluanit.bookingticket_guest.Adapter.RouteAdapter;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class BookingHistory extends AppCompatActivity {

    ImageView back;
    ArrayList<Route> historyArrayList;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        back = (ImageView) findViewById(R.id.back_pressed_history);

        historyArrayList = new ArrayList<>();
        historyArrayList.add(new Route("Sài Gòn",
                "Nha Trang", Home.currentTicket.getDay(),
                "8:00", "14:00", "200000"));

        historyArrayList.add(new Route("Sài Gòn",
                "Nha Trang", Home.currentTicket.getDay(),
                "8:00", "14:00", "200000"));

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
            }
        });
    }
}
