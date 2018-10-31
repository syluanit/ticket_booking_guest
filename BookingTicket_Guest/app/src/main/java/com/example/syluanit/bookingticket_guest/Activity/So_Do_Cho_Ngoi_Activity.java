package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class So_Do_Cho_Ngoi_Activity extends AppCompatActivity {

    Button btn_Dat_Ve;
    ImageView back, seatSuggestion;
    ArrayList<GheNgoi> gheNgoiArrayList;
    GridView gridView;
    public static So_Do_Xe_Adapter_Scrolling adapter;
    LinearLayout giuong, ghe;
    Database database;

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

//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    int seatStatus = gheNgoiArrayList.get(position).getTrangThai();
//                    if (seatStatus == 0){
//                      currentSeat.add(gheNgoiArrayList.get(position));
//                        gheNgoiArrayList.get(position).setTrangThai(1) ;
//
//                    } else {
//                        for (int i = 0; i < currentSeat.size(); i++) {
//                            if (currentSeat.get(i).getViTri().equals(gheNgoiArrayList.get(position).getViTri())) {
//                                currentSeat.remove(i);
//                                break;
//                            }
//                        }
//                        gheNgoiArrayList.get(position).setTrangThai(0);
//                    }
//                    setSeatPositionText();
//                    adapter.notifyDataSetChanged();
//                }
//            });

//            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
////                    Toast.makeText(getApplication(), "" + position , Toast.LENGTH_SHORT).show();
//                    gheNgoiArrayList.get(position).setTrangThai(2) ;
//                    adapter.notifyDataSetChanged();
//                    return false;
//                }
//            });
        }

        btn_Dat_Ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSeat.size() != 0) {
                        if (currentSeat != null) {
                            String seat = "";
                            String seatId = "";

//                            for (int i = 0; i < currentSeat.size(); i++) {
//                                if (i != currentSeat.size() - 1) {
//                                    seat.add(currentSeat.get(i).getViTri());
//                                } else
//                                    seat.add(currentSeat.get(i).getViTri() );
//                            }
                            for (int i = 0; i < currentSeat.size(); i++) {
                                if (i != currentSeat.size() - 1) {
                                    seat += (currentSeat.get(i).getViTri() + ", ");
                                    seatId += (currentSeat.get(i).getId() + ",");
                                } else {seat += (currentSeat.get(i).getViTri() + ".");
                                    seatId += (currentSeat.get(i).getId());
                                }
                            }
//                            Toast.makeText(So_Do_Cho_Ngoi_Activity.this, seatId + "", Toast.LENGTH_SHORT).show();
                            Home.currentTicket.setSeat(seat);
                            Home.currentTicket.setSeatId(seatId);
                            Home.currentTicket.setNumSeat(currentSeat.size());
                        }


                   database.queryData("INSERT INTO Ticket VALUES(null, '"+ Home.currentTicket.getId() +"'," +
                           " '"+ Home.currentTicket.getStartDestination() +"'," +
                           " '"+ Home.currentTicket.getEndDestination() +"'," +
                            " '"+ Home.currentTicket.getDay() +"'," +
                           " '"+ Home.currentTicket.getTimeDep()  +"'," +
//                           " '"+ Home.currentTicket.getTimeArr() +"'," +
                           " '14:00', " +
                           " '" + Home.currentTicket.getPrice()  +"'," +
                           " '" + Home.currentTicket.getSeat()  +"'," +
                           " '" + Home.currentTicket.getSeatId()  +"'," +
                           "'" + Home.currentTicket.getNumSeat() + "'," +
                           "'"+ Home.currentTicket.getTypeSeat()+"')");

                    Intent intent = new Intent(So_Do_Cho_Ngoi_Activity.this, Fill_Customer_Info_Form.class);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) So_Do_Cho_Ngoi_Activity.this).onBackPressed();
            }
        });

        seatSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(So_Do_Cho_Ngoi_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_seat_suggestion);
                final RangeSeekBar seekBar = (RangeSeekBar) dialog.findViewById(R.id.ageSeekbar);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupSeat) ;

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.radioButtonSeatMen:
                            case R.id.radioButtonWomen:
                        }
                    }
                });

                seekBar.setRangeValues(0,99);
                seekBar.setSelectedMinValue(18);
                seekBar.setSelectedMaxValue(50);
                seekBar.setTextAboveThumbsColor(ContextCompat.getColor(getApplication(), R.color.color_text_route));
                seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

//                        Toast.makeText(So_Do_Cho_Ngoi_Activity.this,  minValue.toString() + "A" +seekBar.getSelectedMinValue(), Toast.LENGTH_SHORT).show();
                    }
                });
//
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        // Connect Database

    }

    private void setSeatPositionText (){
        if (currentSeat != null) {
            String seat = "";
            for (int i = 0; i < currentSeat.size(); i++) {
                if (i != currentSeat.size() - 1) {
                    seat += (currentSeat.get(i).getViTri() + ", ");
                } else seat += (currentSeat.get(i).getViTri() + ".");
            }
            tv_seatSelected.setText(seat);
        }
    }
}
