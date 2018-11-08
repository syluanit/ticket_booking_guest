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
import com.example.syluanit.bookingticket_guest.Adapter.TravelHistoryAdapter;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.Model.Route;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TravelHistory extends AppCompatActivity {

    ImageView back;
    ArrayList<CurrentTicket> historyArrayList;
    TravelHistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    private String  idCheck;
    private StringBuilder positionCheck;
    TextView noHistory;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);

        back = (ImageView) findViewById(R.id.back_pressed_travelhistory);
        noHistory = (TextView) findViewById(R.id.noHistory);

        historyArrayList = new ArrayList<>();

        idCheck = "";
        positionCheck = new StringBuilder();
//        historyArrayList.add(new Route("Sài Gòn",
//                "Nha Trang", Home.currentTicket.getDay(),
//                "8:00", "14:00", "200000"));
//
//        historyArrayList.add(new Route("Sài Gòn",
//                "Nha Trang", Home.currentTicket.getDay(),
//                "8:00", "14:00", "200000"));

        recyclerView = (RecyclerView) findViewById(R.id.rv_TravelBooking);
        recyclerView.setHasFixedSize(true);
        historyAdapter = new TravelHistoryAdapter(this, historyArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(historyAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) TravelHistory.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
            }
        });

        final String url = "http://192.168.43.218/busmanager/public/lichsuAndroid";
        sendUserData(url);
    }

    private void sendUserData(String url) {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("ve");
                            if (!jsonArray.toString().equals("[]")){
                            Log.d("", "onResponse: ");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                String start = jsonObject1.getString("Nơi_đi");
                                String end = jsonObject1.getString("Nơi_đến");
                                String date = jsonObject1.getString("Ngày_xuất_phát");
                                String timeStart = jsonObject1.getString("Giờ_xuất_phát");
                                String timeArr = jsonObject1.getString("Thời_gian_đến_dự_kiến");
                                String price = jsonObject1.getString("Tiền_vé");
                                price = currencyFormat(price);
                                String [] s = timeArr.split("\\s+");
                                timeArr = s[1];
                                String position = jsonObject1.getString("Vị_trí_ghế");
                                String routeId = jsonObject1.getString("Mã");
                                int typeSeat = jsonObject1.getInt("Loại_ghế");
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                historyArrayList.add(new CurrentTicket(routeId, start, end, date,
                                        simpleDateFormat.format(simpleDateFormat.parse(timeStart)),
                                        simpleDateFormat.format(simpleDateFormat.parse(timeArr)),
                                        price, position, typeSeat, 1));
//
//                                if (i == 0){
//                                    idCheck = routeId;
//                                    positionCheck.append(position + ", ");
//                                }
//                                else if (routeId.equals(idCheck)){
//                                    positionCheck.append(position +", ");
//                                } else {
//
////                                    Toast.makeText(TravelHistory.this, s[0] + "", Toast.LENGTH_SHORT).show();
////                                    ArrayList <GheNgoi> s1 = new ArrayList<>();
////                                    for (int j = 0; j < s.length; j++){
////                                        s1.get(j).setViTri(s[j]);
////                                    }
//                                    historyArrayList.add(new CurrentTicket(routeId, start, end, date,
//                                            timeStart, "14:00", price, positionCheck.toString(), typeSeat, 1));
//                                    positionCheck = new StringBuilder();
//                                    positionCheck.append(position + ", ");
//                                    if (i == jsonArray.length() - 1) {
//                                        historyArrayList.add(new CurrentTicket(routeId, start, end, date,
//                                                timeStart, "14:00", price, positionCheck.toString(), typeSeat, 1));
//                                    }
//                                    idCheck = routeId;
//                                }
                                }
                            }
                            else {
                                noHistory.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        historyAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        noHistory.setVisibility(View.VISIBLE);
                        Toast.makeText(TravelHistory.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }) {
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

                params.put("ID", userId);
                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
