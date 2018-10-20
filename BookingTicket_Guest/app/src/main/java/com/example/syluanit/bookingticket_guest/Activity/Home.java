package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.Model.CurrentUser;
import com.example.syluanit.bookingticket_guest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DIA_DIEM_ACTIVITY_REQUEST_CODE = 100;
    private static final int DIA_DIEM_TO_ACTIVITY_REQUEST_CODE = 200;
//    String url = "http://192.168.40.68/laravel/getTicket";
    String url = "http://192.168.1.214/laravel/getTicket";

    private EditText et_pickDay,et_from, et_to;
    private String typeSeat;
    private RadioGroup radioGroupTypeSeat;
    private RelativeLayout layout_font;
    private Button btn_ticketSearch;
    public static CurrentTicket currentTicket;
    public static CurrentUser currentUser;
    public static NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Calligrapher calligrapher = new Calligrapher(this);
//        calligrapher.setFont(this,"Signatra.ttf", true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        et_pickDay = (EditText) findViewById(R.id.pickDay);
        et_from = (EditText) findViewById(R.id.et_from);
        et_to = (EditText) findViewById(R.id.et_to);
        btn_ticketSearch = (Button) findViewById(R.id.btn_ticketSearch);
        currentTicket = new CurrentTicket();
        currentUser =  new CurrentUser();

        // TODO Hiding soft keyboard on editext
        et_pickDay.setFocusable(false);
        et_pickDay.setClickable(true);
        et_pickDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDay();
            }
        });

        btn_ticketSearch.bringToFront();
        btn_ticketSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = et_from.getText().toString();
                String to = et_to.getText().toString();
                String date = et_pickDay.getText().toString();

                currentTicket.setStartDestination(from);
                currentTicket.setEndDestination(to);
                currentTicket.setDay(date);

                // TODO Checking infomation
                if (from.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn điểm đi!", Toast.LENGTH_LONG).show();
                } else if (to.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn điểm đến!", Toast.LENGTH_LONG).show();
                } else if (date.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn ngày đi!", Toast.LENGTH_LONG).show();
                } else {
//                 TODO Sending data to the TimeList Activity using Bundle
                    final String url = "http://192.168.43.218/busmanager/public/chuyenxeAndroid";
                    sendUserData(url);
            }
            }
        });

        et_from.setFocusable(false);
        et_from.setClickable(true);
        et_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                startActivityForResult(intent, DIA_DIEM_ACTIVITY_REQUEST_CODE);
            }
        });

        et_to.setFocusable(false);
        et_to.setClickable(true);
        et_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                startActivityForResult(intent, DIA_DIEM_TO_ACTIVITY_REQUEST_CODE);
            }
        });

        radioGroupTypeSeat = (RadioGroup) findViewById(R.id.radioGroup) ;
        currentTicket.setTypeSeat(1);
        typeSeat = "1";
        radioGroupTypeSeat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        currentTicket.setTypeSeat(0);
                        typeSeat = "0";
                        break;
                    case R.id.radioButton2:
                        currentTicket.setTypeSeat(1);
                        //giuong
                        typeSeat = "1";
                        break;
                }
            }
        });

//        String url = "http://192.168.1.214/laravel/receiveDataUser";

//        final String url = "http://192.168.43.218/busmanager/public/chonveAndroid";
//        final String url = "http://192.168.43.218/busmanager/public/lichsuAndroid";

    }

    private void sendUserData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: yeahyeah");
//                        Toast.makeText(Home.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onResponse: " + response.toString());
                        Bundle ticket = new Bundle();
                        ticket.putString("ticketJson",response);
                        Intent intent = new Intent(Home.this, RouteActivity.class);
                        intent.putExtras(ticket);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
//                params.put("X-CSRF-Token", accessToken);
                params.put("Noidi", et_from.getText().toString().trim());
                params.put("Noiden", et_to.getText().toString().trim());
                params.put("Loaighe", typeSeat);
                params.put("Ngaydi", et_pickDay.getText().toString().trim());
                Log.d("AAA", "getParams: OK!!!");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//
//private void sendUserData(String url){
//
//    final RequestQueue requestQueue = Volley.newRequestQueue(this);
//    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d("AAA", "onResponse: yeahyeah");
//                    Toast.makeText(Home.this, response.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("AAA", "onResponse: " + response.toString());
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
//                    Log.d("AAA", "onErrorResponse: " + error.toString());
//                }
//            }){
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//
//            Map<String, String> params = new HashMap<>();
//            params.put("ID", "1");
//            Log.d("AAA", "getParams: OK!!!");
//            return params;
//        }
//    };
//    requestQueue.add(stringRequest);
//}

    private void receiveUserData (String url){
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);

//                JSONArray jsonArray = new JSONArray();
//                try {
//                    jsonArray = new JSONArray(url);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(Home.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIA_DIEM_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                String dia_diem = data.getStringExtra("diadiem");
                et_from.setText(dia_diem);
            }
        }
        else if (requestCode ==  DIA_DIEM_TO_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                String dia_diem = data.getStringExtra("diadiem");
                et_to.setText(dia_diem);
            }
        }
    }

    private void pickDay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay, thang, nam = 0;
        if (et_pickDay.getText().toString().matches("")) {
            ngay = calendar.get(Calendar.DATE);
             thang = calendar.get(Calendar.MONTH);
             nam = calendar.get(Calendar.YEAR);
        }
        else {
            String s = et_pickDay.getText().toString();
            String [] arrayString = s.split("-");
            ngay = Integer.parseInt(arrayString[0]);
            thang = Integer.parseInt(arrayString[1]);
            nam = Integer.parseInt(arrayString[2]);
        }

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month,dayOfMonth);
                // TODO Time format and set picked day to the edittext

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                et_pickDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final Dialog dialog = new Dialog(Home.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_exit);
            Button btn_exit = dialog.findViewById(R.id.btn_cancel);
            Button btn_accept = dialog.findViewById(R.id.btn_accept);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) Home.this).finish();
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // item.getGroupId()
        int id = item.getItemId();


//        item.setChecked(true);
//        drawerLayout.closeDrawers();
        if (id == R.id.nav_Booking) {
            // Handle the camera action
        } else if (id == R.id.nav_BookingHistory) {
            Intent intent = new Intent(Home.this, BookingHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_Login_SignUp) {
            Intent intent = new Intent(Home.this, Dang_Nhap_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(Home.this, UserInfo.class);
            startActivity(intent);
        } else if (id == R.id.nav_Introduction) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
