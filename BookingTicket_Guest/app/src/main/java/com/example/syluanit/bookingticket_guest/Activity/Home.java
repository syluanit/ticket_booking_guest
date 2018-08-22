package com.example.syluanit.bookingticket_guest.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.syluanit.bookingticket_guest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DIA_DIEM_ACTIVITY_REQUEST_CODE = 100;
    private static final int DIA_DIEM_TO_ACTIVITY_REQUEST_CODE = 200;

    EditText et_pickDay,et_from, et_to;
    Button btn_ticketSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        et_pickDay = (EditText) findViewById(R.id.pickDay);
        et_from = (EditText) findViewById(R.id.et_from);
        et_to = (EditText) findViewById(R.id.et_to);
        btn_ticketSearch = (Button) findViewById(R.id.btn_ticketSearch);


        // TODO Hiding soft keyboard on editext
        et_pickDay.setFocusable(false);
        et_pickDay.setClickable(true);
        et_pickDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pickDay();
            }
        });


        btn_ticketSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = et_from.getText().toString();
                String to = et_to.getText().toString();
                String date = et_pickDay.getText().toString();
                // TODO Checking infomation
//                if (from.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn điểm đi!", Toast.LENGTH_LONG).show();
//                } else if (to.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn điểm đến!", Toast.LENGTH_LONG).show();
//                } else if (date.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn ngày đi!", Toast.LENGTH_LONG).show();
//                } else {
                // TODO Sending data to the TimeList Activity using Bundle
                Intent intent = new Intent(Home.this, TimeListActivity.class);
                Bundle ticket = new Bundle();
                ticket.putString("from", from);
                ticket.putString("to", to);
                ticket.putString("date", date);
                intent.putExtras(ticket);
                startActivity(intent);
//                }
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
        final Calendar calendar =  Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month,dayOfMonth);
                // TODO Time format and set picked day to the edittext
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                et_pickDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        item.setChecked(true);
//        drawerLayout.closeDrawers();
        if (id == R.id.nav_Booking) {
            // Handle the camera action
        } else if (id == R.id.nav_BookingHistory) {

        } else if (id == R.id.nav_Login_SignUp) {
            Intent intent = new Intent(Home.this, Dang_Nhap_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {

        } else if (id == R.id.nav_Introduction) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
