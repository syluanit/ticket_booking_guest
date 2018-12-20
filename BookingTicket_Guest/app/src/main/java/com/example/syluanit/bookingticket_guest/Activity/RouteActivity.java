package com.example.syluanit.bookingticket_guest.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syluanit.bookingticket_guest.Adapter.RouteAdapter;
import com.example.syluanit.bookingticket_guest.Model.DiaDiem;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RouteActivity extends AppCompatActivity {

    ImageView back_pressed;
    ArrayList<Route> routeArrayList;
    RouteAdapter routeAdapter;
    RecyclerView recyclerView;
    TextView start, end, date;
    public static TextView noRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        back_pressed = (ImageView) findViewById(R.id.timelist_back_pressed);
        start = (TextView) findViewById(R.id.tv_start);
        end = (TextView) findViewById(R.id.tv_end);
        date = (TextView) findViewById(R.id.tv_day);
        noRoute = (TextView) findViewById(R.id.noRoute);

        routeArrayList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String receiveJson = bundle.getString("ticketJson", "");
            try {
                JSONObject jsonObject = new JSONObject(receiveJson);
                JSONArray jsonArray = jsonObject.getJSONArray("chuyenxe");
                if (!jsonArray.toString().equals("[]")){
                    noRoute.setVisibility(View.GONE);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String time = jsonObject1.getString("Giờ_xuất_phát");
                    String price = jsonObject1.getString("Tiền_vé");
                    String id = jsonObject1.getString("Mã");
                    String timeArr = jsonObject1.getString("Thời_gian_đến_dự_kiến");
                    String [] s = timeArr.split("\\s+");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                    routeArrayList.add(new Route(simpleDateFormat.format(simpleDateFormat1.parse(time)),
                            simpleDateFormat.format(simpleDateFormat1.parse(s[1])), currencyFormat(price), id));
                }}
                else {
                    noRoute.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(receiveJson);
                String startShow = jsonObject.getString("from");
                String endShow = jsonObject.getString("to");
                String timeShow = jsonObject.getString("tt");
                start.setText(startShow);
                end.setText(endShow);
                date.setText(timeShow);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.rv_Route);
        recyclerView.setHasFixedSize(true);
        routeAdapter = new RouteAdapter(this, routeArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(routeAdapter);

        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RouteActivity.this).onBackPressed();
            }
        });
    }

    // TODO transphorm the price
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}
