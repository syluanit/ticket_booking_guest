package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Model.CurrentTicket;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DIA_DIEM_ACTIVITY_REQUEST_CODE = 100;
    private static final int DIA_DIEM_TO_ACTIVITY_REQUEST_CODE = 200;

    private EditText et_pickDay,et_from, et_to;
    private String typeSeat;
    private RadioGroup radioGroupTypeSeat;
    private RelativeLayout layout_font;
    private Button btn_ticketSearch;
    public static CurrentTicket currentTicket;
    public static boolean routeSignal = false;
    public static NavigationView navigationView;
//    String url = "http://192.168.43.218/busmanager/public/chuyenxeAndroid";
    String url;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("AAA", "onCreate: ");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set phont
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
        routeSignal = false;
        // TODO Hiding soft keyboard on editext
        et_pickDay.setFocusable(false);
        et_pickDay.setClickable(true);
        et_pickDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDay();
            }
        });

        // TODO click searching route button
        btn_ticketSearch.bringToFront();

        btn_ticketSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = et_from.getText().toString();
                String to = et_to.getText().toString();
                String date = et_pickDay.getText().toString();
                // TODO information checking
                if (from.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn điểm đi!", Toast.LENGTH_LONG).show();
                } else if (to.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn điểm đến!", Toast.LENGTH_LONG).show();
                } else if (date.isEmpty()) {
                    Toast.makeText(Home.this, "Vui lòng chọn ngày đi!", Toast.LENGTH_LONG).show();
                } else {
                    String ip = getResources().getString(R.string.ip);
                    String address = getResources().getString(R.string.address);
                    url = ip + address + "/chuyenxeAndroid";
                    sendRouteData(url);
            }

                currentTicket.setStartDestination(from);
                currentTicket.setEndDestination(to);
                currentTicket.setDay(date);

            }
        });

        et_from.setFocusable(false);
        et_from.setClickable(true);
        // TODO starting point click event
        et_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_to.getText().toString().matches("")){
                    Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                    startActivityForResult(intent, DIA_DIEM_ACTIVITY_REQUEST_CODE);
                }
                else
                {
                    Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                    intent.putExtra("from", et_to.getText().toString());
                    startActivityForResult(intent, DIA_DIEM_ACTIVITY_REQUEST_CODE);
                }
            }
        });

        et_to.setFocusable(false);
        et_to.setClickable(true);
        // TODO destination click event
        et_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_from.getText().toString().matches("")){
                    Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                    startActivityForResult(intent, DIA_DIEM_TO_ACTIVITY_REQUEST_CODE);
                }
                else
                {
                    Intent intent = new Intent(Home.this, Chon_Dia_Diem.class);
                    intent.putExtra("to", et_from.getText().toString());
                    startActivityForResult(intent, DIA_DIEM_TO_ACTIVITY_REQUEST_CODE);
                }
            }
        });


        radioGroupTypeSeat = (RadioGroup) findViewById(R.id.radioGroup) ;
        // set dephault value is Woman
        currentTicket.setTypeSeat(1);
        typeSeat = "1";
        radioGroupTypeSeat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        //ghe
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

        database = new Database(this, "ticket.sqlite", null, 1);
//        database.queryData("Drop table IF exists Ticket");

        Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");
        // checking weather User signed or not
        if (data.getCount() > 0){
            // TODO set visible tag
            navigationView.getMenu().findItem(R.id.nav_Login_SignUp).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_SignUp).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_TravelHistory).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_user_menu).setVisible(true);

            Cursor currentUserDB = database.getDaTa("Select * from User");
            while (currentUserDB.moveToNext()) {
                TextView tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_username);
                tv.setText(currentUserDB.getString(7));
            }
        }

        // TODO create table Ticket
        database.queryData("CREATE TABLE IF NOT EXISTS Ticket(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " routeId INTEGER, start VARCHAR(200), end VARCHAR(200), " +
                "date VARCHAR(200), timeStart VARCHAR(200), timeArr VARCHAR(200), " +
                "price VARCHAR(200), seat VARCHAR(200), seatId VARCHAR(200), numSeat INTEGER, typeSeat INTEGER)");

    }

    // TODO using Volley to send a request to server, background thread (worker thread) with post method
    private void sendRouteData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: " + response.toString());
                        Bundle ticket = new Bundle();
                        ticket.putString("ticketJson",response);
                        Intent intent = new Intent(Home.this, RouteActivity.class);
                        intent.putExtras(ticket);
                        startActivity(intent);
                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
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

    // TODO set text to the staring point and ending point aphter choose the provinces
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking the result phor two dierent start and destination places
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

        // TODO checking weather date phorm is empty or not
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
        }, nam, thang - 1, ngay);

        // TODO disable the past date
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
            Intent intent = new Intent(Home.this, Contact.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_TravelHistory) {
            Intent intent = new Intent(Home.this, TravelHistory.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_Logout) {
            final Dialog dialog = new Dialog(Home.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_exit);
            TextView content = dialog.findViewById(R.id.content);
            Button btn_exit = dialog.findViewById(R.id.btn_cancel);
            Button btn_accept = dialog.findViewById(R.id.btn_accept);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            content.setText("Bạn có muốn đăng xuất?");
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.queryData("Drop table IF exists User");
                    finish();
                    startActivity(getIntent());
                }
            });
            dialog.show();
        }
        else if (id == R.id.nav_SignUp) {
            Intent intent = new Intent(Home.this,Dang_Ky_Activity.class);
            startActivity(intent);
        }else if (id == R.id.nav_user_menu) {
            Intent intent = new Intent(Home.this, UserInfo.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        Log.d("AAA", "onPause: ");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("AAA", "onResume: HOme");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("AAA", "onStart: HOme");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("AAA", "onStop: HOme ");
        super.onStop();
    }
}
