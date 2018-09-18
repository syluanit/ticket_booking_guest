package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.Adapter.RouteAdapter;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    ImageView back_pressed;
    ArrayList<Route> routeArrayList;
    RouteAdapter routeAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        back_pressed = (ImageView) findViewById(R.id.timelist_back_pressed);

        routeArrayList = new ArrayList<>();
        routeArrayList.add(new Route(Home.currentTicket.getStartDestination(),
                Home.currentTicket.getEndDestination(), Home.currentTicket.getDay(),
                "8:00", "14:00", "200000"));
        routeArrayList.add(new Route(Home.currentTicket.getStartDestination(),
                Home.currentTicket.getEndDestination(), Home.currentTicket.getDay(),
                "8:00", "14:00", "200000"));
        routeArrayList.add(new Route("8:00", "14:00", "200000"));
        routeArrayList.add(new Route("8:00", "14:00", "200000"));
        routeArrayList.add(new Route("8:00", "14:00", "200000"));
        routeArrayList.add(new Route("8:00", "14:00", "200000"));
        routeArrayList.add(new Route("8:00", "14:00", "200000"));

        recyclerView = (RecyclerView) findViewById(R.id.rv_Route);
        recyclerView.setHasFixedSize(true);
        routeAdapter = new RouteAdapter(this, routeArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(routeAdapter);

        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RouteActivity.this).onBackPressed();
            }
        });
    }
}
