package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter;
import com.example.syluanit.bookingticket_guest.Adapter.ViewPagerAdapter;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class So_Do_Cho_Ngoi_Activity extends AppCompatActivity {

    Button btn_Dat_Ve;
    ImageView back, seatSuggestion;
    ArrayList<GheNgoi> gheNgoiArrayList;
    GridView gridView;
    So_Do_Xe_Adapter adapter;
    LinearLayout giuong, ghe;

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

        currentSeat = new ArrayList<>();

        Intent intent = getIntent();
        TicketMap  = intent.getStringExtra("ticketMap");

        if (Home.currentTicket.getTypeSeat() == 1 ) {
            // Giuong nam
            giuong.setVisibility(View.VISIBLE);
            ghe.setVisibility(View.GONE);
            TabLayout tabLayout =  findViewById(R.id.myTabLayout);
            ViewPager viewPager = findViewById(R.id.myViewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new Fragment_Tang_Tren(), "Tầng Trên");
            viewPagerAdapter.addFragment(new Fragment_Tang_Duoi(), "Tầng Dưới");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
        else {
            giuong.setVisibility(View.GONE);
            ghe.setVisibility(View.VISIBLE);
            // Ghe Ngoi
            gridView = (GridView) findViewById(R.id.gridviewGheNgoi);

            int j = 1;
            for (int i = 0; i < 35; i++) {
                if (i == 4) {
                    gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat,1,"A" + j ,0));
                    j++;
                }
                else
                if ( (i % 5)  == 2) {
                    gheNgoiArrayList.add(new GheNgoi(0, "" ));
                } else {
                    gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, "A" + j));
                    j++;
                }
            }

            adapter = new So_Do_Xe_Adapter(this, R.layout.giuong_nam_item, gheNgoiArrayList);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int seatStatus = gheNgoiArrayList.get(position).getTrangThai();
                    if (seatStatus == 0){
                        So_Do_Cho_Ngoi_Activity.currentSeat.add(gheNgoiArrayList.get(position));
                        gheNgoiArrayList.get(position).setTrangThai(1) ;

                    } else {
                        for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                            if (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri().equals(gheNgoiArrayList.get(position).getViTri())) {
                                So_Do_Cho_Ngoi_Activity.currentSeat.remove(i);
                                break;
                            }
                        }
                        gheNgoiArrayList.get(position).setTrangThai(0);
                    }
                    setSeatPositionText();
                    adapter.notifyDataSetChanged();
                }

            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplication(), "" + position , Toast.LENGTH_SHORT).show();
                    gheNgoiArrayList.get(position).setTrangThai(2) ;
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });


        }

        btn_Dat_Ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSeat.size() != 0) {
                        if (So_Do_Cho_Ngoi_Activity.currentSeat != null) {
                            ArrayList<String> seat = new ArrayList<>();

                            for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                                if (i != So_Do_Cho_Ngoi_Activity.currentSeat.size() - 1) {
                                    seat.add(So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri());
                                } else
                                    seat.add(So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() );
                            }
                            Home.currentTicket.setSeat(seat);
//                            for (int i = 0; i < Home.currentTicket.getSeat().size(); i++) {
//                                String seat1 = "";
//                                if (i != Home.currentTicket.getSeat().size() - 1) {
//                                    seat1 += (Home.currentTicket.getSeat().get(i).toString() + ", ");
//                                } else seat1 += (Home.currentTicket.getSeat().get(i).toString() + ".");
//                            }
                        }

                    Home.currentTicket.setNumSeat(currentSeat.size());

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
                dialog.setContentView(R.layout.dialog_seat_suggestion);
                TextView tv_ok = (TextView) dialog.findViewById(R.id.annoucement_suggestion);
                TextView seat = (TextView) dialog.findViewById(R.id.seat_suggestion);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });



        // Connect Database

    }

    private void setSeatPositionText (){
        if (So_Do_Cho_Ngoi_Activity.currentSeat != null) {
            String seat = "";
            for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                if (i != So_Do_Cho_Ngoi_Activity.currentSeat.size() - 1) {
                    seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ", ");
                } else seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ".");
            }
            So_Do_Cho_Ngoi_Activity.tv_seatSelected.setText(seat);
        }
    }

    private void sendUserData(String url, final String id){

    final RequestQueue requestQueue = Volley.newRequestQueue(this);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("AAA", "onResponse: yeahyeah");
//                    Toast.makeText(So_Do_Cho_Ngoi_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("AAA", "onResponse: " + response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("sodo");
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                        String sodo = jsonObject1.getString("Sơ_đồ");
                        TicketMap = sodo;
                        Toast.makeText(So_Do_Cho_Ngoi_Activity.this,TicketMap + "", Toast.LENGTH_SHORT).show();

//                        int j = 1;
//                        for (int i = 0; i < 35; i++) {
//                            if (i == 4) {
//                                gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat,1,"A" + j ,0));
//                                j++;
//                            }
//                            else
//                            if ( (i % 5)  == 2) {
//                                gheNgoiArrayList.add(new GheNgoi(0, "" ));
//                            } else {
//                                gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, "A" + j));
//                                j++;
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(So_Do_Cho_Ngoi_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                    Log.d("AAA", "onErrorResponse: " + error.toString());
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> params = new HashMap<>();
            params.put("ID", id);
            Log.d("AAA", "getParams: OK!!!");
            return params;
        }
    };
    requestQueue.add(stringRequest);
}

}
