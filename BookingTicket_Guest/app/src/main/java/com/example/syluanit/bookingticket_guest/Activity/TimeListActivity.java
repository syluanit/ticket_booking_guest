package com.example.syluanit.bookingticket_guest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.syluanit.bookingticket_guest.Adapter.TimeListAdapter;
import com.example.syluanit.bookingticket_guest.Model.TimeList;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class TimeListActivity extends AppCompatActivity {

    ListView lv_timeList;
    TimeListAdapter adapterTimeList;
    ArrayList<TimeList> timeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lv_timeList = (ListView) findViewById(R.id.lv_timeList);

        timeList = new ArrayList<>();
        timeList.add(new TimeList("8:00", R.drawable.front_bus));
        timeList.add(new TimeList("9:00", R.drawable.front_bus));
        timeList.add(new TimeList("10:00", R.drawable.front_bus));

        Intent intent = getIntent();
        Bundle ticket = intent.getExtras();
        if (ticket != null) {
            String from = ticket.getString("from", "");
            String to = (ticket.getString("to", ""));
            String date = (ticket.getString("date", ""));
        }

        adapterTimeList = new TimeListAdapter(this, R.layout.dong_thoi_gian, timeList);
        lv_timeList.setAdapter(adapterTimeList);

        lv_timeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =  new Intent(TimeListActivity.this, So_Do_Cho_Ngoi_Activity.class);
                startActivity(i);
            }
        });
    }
}
