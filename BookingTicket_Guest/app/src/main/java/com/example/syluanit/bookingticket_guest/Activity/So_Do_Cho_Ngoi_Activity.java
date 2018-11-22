package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter_Scrolling;
import com.example.syluanit.bookingticket_guest.Adapter.ViewPagerAdapter;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class So_Do_Cho_Ngoi_Activity extends AppCompatActivity {

    private Boolean choice;
    Button btn_Dat_Ve;
    ImageView back, seatSuggestion;
    ArrayList<GheNgoi> gheNgoiArrayList;
    GridView gridView;
    public static So_Do_Xe_Adapter_Scrolling adapter;
    LinearLayout giuong, ghe;
    Database database;
    Dialog dialog;

    public static TextView tv_seatSelected;
    public static ArrayList<GheNgoi> currentSeat;
    public static String TicketMap = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so__do__cho__ngoi_);

        btn_Dat_Ve = findViewById(R.id.btn_dat_ve);
        back  = (ImageView) findViewById(R.id.so_do_back_pressed);
        seatSuggestion = (ImageView) findViewById(R.id.iv_suggestion);
        tv_seatSelected = (TextView) findViewById(R.id.tv_seatSelected);
        giuong = (LinearLayout) findViewById(R.id.layout_giuong_nam);
        ghe = (LinearLayout ) findViewById(R.id.layout_ghe_ngoi);

        database = new Database(this, "ticket.sqlite", null, 1);

        currentSeat = new ArrayList<>();
        gheNgoiArrayList = new ArrayList<>();

        Intent intent = getIntent();
        TicketMap  = intent.getStringExtra("ticketMap");

        // show sơ đô xe giương hoặc ghế ngôi
        if (Home.currentTicket.getTypeSeat() == 1 ) {
            // Giuong nam
            giuong.setVisibility(View.VISIBLE);
            ghe.setVisibility(View.GONE);
            TabLayout tabLayout =  findViewById(R.id.myTabLayout);
            ViewPager viewPager = findViewById(R.id.myViewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new Fragment_Tang_Tren(), "Tầng Dưới");
            viewPagerAdapter.addFragment(new Fragment_Tang_Duoi(), "Tầng Trên");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
        else {
            giuong.setVisibility(View.GONE);
            ghe.setVisibility(View.VISIBLE);
            // Ghe Ngoi
//            gridView = (GridView) findViewById(R.id.gridviewGheNgoi);
            try {
                JSONObject jsonObject = new JSONObject(TicketMap);
                JSONArray jsonArray = jsonObject.getJSONArray("ve");
                JSONArray jsonArraySoDo = jsonObject.getJSONArray("sodo");
                JSONObject jsonObject1 = (JSONObject) jsonArraySoDo.get(0);
                String s = jsonObject1.getString("Sơ_đồ");

                String [] sodo = s.split("(?!^)");
                int j = 0;
                for (int i = 0; i < sodo.length; i++) {
                        if (i == 0){
                            gheNgoiArrayList.add(new GheNgoi(null,R.mipmap.ic_driver, 0, "", 0));
                        }
                        else
                        if (sodo[i].equals("1")){
                            JSONObject jsonObjectTicket = (JSONObject) jsonArray.get(j);
                            String seatId = jsonObjectTicket.getString("Mã");
                            if (jsonObjectTicket.getString("Trạng_thái").equals("1")) {
                                gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 2));
                                j++;
                            }
                            else if (jsonObjectTicket.getString("Trạng_thái").equals("0")) {
                                gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 0));
                                j++;
                            }
                            else if (jsonObjectTicket.getString("Trạng_thái").equals("2")) {
                                gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 2));
                                j++;
                            }
                        }
                        else {
                            gheNgoiArrayList.add(new GheNgoi(0, "" ));
                        }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new So_Do_Xe_Adapter_Scrolling(this,  gheNgoiArrayList);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_GiuongNam);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new GridLayoutManager(this, 6);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }

        //click button đặt vé
        btn_Dat_Ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSeat.size() != 0) {
                        if (currentSeat != null) {
                            String seat = "";
                            String seatId = "";
                            // prepare seat and seatId list
                            for (int i = 0; i < currentSeat.size(); i++) {
                                if (i != currentSeat.size() - 1) {
                                    seat += (currentSeat.get(i).getViTri() + ", ");
                                    seatId += (currentSeat.get(i).getId() + ",");
                                } else {seat += (currentSeat.get(i).getViTri() + ".");
                                    seatId += (currentSeat.get(i).getId());
                                }
                            }
//                            Toast.makeText(So_Do_Cho_Ngoi_Activity.this, seatId + "", Toast.LENGTH_SHORT).show();
                            //set seat and seatID value and numSeat value
                            Home.currentTicket.setSeat(seat);
                            Home.currentTicket.setSeatId(seatId);
                            Home.currentTicket.setNumSeat(currentSeat.size());
                        }


                   database.queryData("INSERT INTO Ticket VALUES(null, '"+ Home.currentTicket.getId() +"'," +
                           " '"+ Home.currentTicket.getStartDestination() +"'," +
                           " '"+ Home.currentTicket.getEndDestination() +"'," +
                            " '"+ Home.currentTicket.getDay() +"'," +
                           " '"+ Home.currentTicket.getTimeDep()  +"'," +
                           " '"+ Home.currentTicket.getTimeArr() +"'," +
//                           " '14:00', " +
                           " '" + Home.currentTicket.getPrice()  +"'," +
                           " '" + Home.currentTicket.getSeat()  +"'," +
                           " '" + Home.currentTicket.getSeatId()  +"'," +
                           "'" + Home.currentTicket.getNumSeat() + "'," +
                           "'"+ Home.currentTicket.getTypeSeat()+"')");

                    Intent intent = new Intent(So_Do_Cho_Ngoi_Activity.this, TicketBookingInfo.class);
                    startActivity(intent);
                } else {
                    final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_no_seat_selected);
                    TextView tv_annoucement = dialog.findViewById(R.id.tv_OK);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tv_annoucement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });
        //end click button đặt vé

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) So_Do_Cho_Ngoi_Activity.this).onBackPressed();
            }
        });

        choice = true;

        //click gợi ý chỗ ngôi
        seatSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = true;
                final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_seat_suggestion_choices);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupSeat_suggestion) ;
                Button suggest = (Button) dialog.findViewById(R.id.btn_suggestion);
                Button back = (Button) dialog.findViewById(R.id.btn_back_suggestion);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.me_suggestion:
                                choice = true;
                                break;
                            case R.id.other_suggestion:
                                choice = false;
                                break;
                        }
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                suggest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.cancel();
                        if (choice){
                            final String url = "http://192.168.43.218/TicketBooking/public/ticketAndroid";
                            sendUserData(url);
                            showProgressDialog();
                        }
                        else {
                            otherSuggestion();
                        }
                    }
                });
                Display display = ((WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setLayout((6* display.getWidth()/7), (display.getHeight() * 15) / 40);
                dialog.show();
            }
        });
        // end gợi ý chỗ ngôi

    }

    private void showProgressDialog (){
        dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
        dialog.setContentView(R.layout.progressdialog);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
        final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.Seekbar);
        seekBar.setMax(100);
        progressBar.setMax(100);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax())
                {
                   current = 0;
                }
                progressBar.setProgress(current + 10);
                seekBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
                dialog.cancel();
            }
        };
        countDownTimer.start();
        dialog.show();
    }

    private void sendUserData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: " + response.toString());
                        dialog.cancel();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.toString().equals("{\"kq\":0}")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("kq");
                                final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_seat_suggestion_result);
                                TextView tv_annoucement = dialog.findViewById(R.id.seat_seat);
                                TextView nextSeat = dialog.findViewById(R.id.seat);
                                Button back = dialog.findViewById(R.id.back_suggestion);

                                back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });

                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                String seat = "";
                                for (int i = 1; i < jsonArray.length(); i++) {
                                    if (i == 4) {
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        seat += jsonObject1.getString("Vị_trí_ghế");
                                        break;
                                    } else {
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        seat += jsonObject1.getString("Vị_trí_ghế") + ", ";
                                    }
                                }
                                nextSeat.setText(seat);
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                                tv_annoucement.setText(jsonObject1.getString("Vị_trí_ghế"));
                                dialog.show();
                            }
                            else {
                                final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_outday);
                                TextView tv_annoucement = dialog.findViewById(R.id.tv_OK_outday);
                                TextView content = dialog.findViewById(R.id.tv_content);
                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                content.setText("Không đủ dữ liệu để gợi ý");
//                                Toast.makeText(Dang_Nhap_Activity.this,response.toString() + "", Toast.LENGTH_SHORT).show();
                                tv_annoucement.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toast.makeText(So_Do_Cho_Ngoi_Activity.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                String userId = "";
                Cursor currentUserDB = database.getDaTa("Select * from User");
                while (currentUserDB.moveToNext()) {
                    userId = currentUserDB.getString(1);
                }

                params.put("idkhachhang", userId);
                params.put("idchuyenxe", Home.currentTicket.getId());
                Log.d("AAA", "getParams: OK!!!");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String gender = "0"
            , tuoimin, tuoimax;

    private void otherSuggestion() {
        final Dialog dialog1 = new Dialog(So_Do_Cho_Ngoi_Activity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_seat_suggestion);
        final RangeSeekBar seekBar = (RangeSeekBar) dialog1.findViewById(R.id.ageSeekbar);
        RadioGroup radioGroup = (RadioGroup) dialog1.findViewById(R.id.radioGroupSeat);
        Button suggest = (Button) dialog1.findViewById(R.id.btn_suggestion);

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                final String url = "http://192.168.43.218/TicketBooking/public/ticketAndroid";
//                                    sendUserData(url);
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.cancel();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (!jsonObject.toString().equals("{\"kq\":0}")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("kq");
                                        final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.dialog_seat_suggestion_result);
                                        TextView tv_annoucement = dialog.findViewById(R.id.seat_seat);
                                        TextView nextSeat = dialog.findViewById(R.id.seat);
                                        Button back = dialog.findViewById(R.id.back_suggestion);

                                        back.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                            }
                                        });
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        String seat = "";
                                        for (int i = 1; i < jsonArray.length(); i++) {
                                            if (i == 4) {
                                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                                seat += jsonObject1.getString("Vị_trí_ghế");
                                                break;
                                            } else {
                                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                                seat += jsonObject1.getString("Vị_trí_ghế") + ", ";
                                            }
                                        }
                                        nextSeat.setText(seat);
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                                        tv_annoucement.setText(jsonObject1.getString("Vị_trí_ghế"));
                                        dialog.show();
                                    }    else    {
                                        final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.dialog_outday);
                                        TextView tv_annoucement = dialog.findViewById(R.id.tv_OK_outday);
                                        TextView content = dialog.findViewById(R.id.tv_content);
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        content.setText("Không đủ dữ liệu để gợi ý");
//                                Toast.makeText(Dang_Nhap_Activity.this,response.toString() + "", Toast.LENGTH_SHORT).show();
                                        tv_annoucement.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                            }
                                        });
                                        dialog.show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("AAA", "onResponse: " + response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.cancel();
                                Toast.makeText(So_Do_Cho_Ngoi_Activity.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                                Log.d("AAA", "onErrorResponse: " + error.toString());
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        String userId = "";
                        Cursor currentUserDB = database.getDaTa("Select * from User");
                        while (currentUserDB.moveToNext()) {
                            userId = currentUserDB.getString(1);
                        }

//                        params.put("idkhachhang", userId);
                        params.put("idchuyenxe", Home.currentTicket.getId());
                        params.put("tuoimin", tuoimin);
                        params.put("tuoimax", tuoimax);
                        params.put("gioitinh", gender);
                        Log.d("AAA", "getParams: OK!!!");

                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        gender = "0";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonSeatMen:
                        gender = "1";
                        break;
                    case R.id.radioButtonWomen:
                        gender = "0";
                        break;
                }
            }
        });

        tuoimin = "18";
        tuoimax = "50";
        seekBar.setRangeValues(0, 99);
        seekBar.setSelectedMinValue(18);
        seekBar.setSelectedMaxValue(50);
        seekBar.setTextAboveThumbsColor(ContextCompat.getColor(getApplication(), R.color.color_text_route));
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                tuoimin = minValue.toString();
                tuoimax = maxValue.toString();

            }
        });
        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog1.show();
    }

    @Override
    protected void onDestroy() {
        Log.d("AAA", "onDestroy: so do cho ngoi");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("AAA", "onStop: So do");
        super.onStop();
    }
}
