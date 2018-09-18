package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.R;

public class Dang_Nhap_Activity extends AppCompatActivity {

    ImageView back;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__nhap_);

        back = (ImageView) findViewById(R.id.sign_in_back_pressed);
        login = (Button) findViewById(R.id.btn_Login);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Dang_Nhap_Activity.this).onBackPressed();

              }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after login successfully
                Home.navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
                Home.navigationView.getMenu().findItem(R.id.nav_BookingHistory).setVisible(true);

            }
        });
    }
    public void quenMatKhau(View v){
        Intent intent = new Intent(this, Lay_Lai_Mat_Khau.class);
        startActivity(intent);
    }
    public void dangKy(View v){
        Intent intent = new Intent(Dang_Nhap_Activity.this, Dang_Ky_Activity.class);
        startActivity(intent);
    }
}
