package com.example.syluanit.bookingticket_guest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.syluanit.bookingticket_guest.R;

public class Dang_Nhap_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__nhap_);
    }
    public void quenMatKhau(View v){

    }
    public void dangKy(View v){
        Intent intent = new Intent(Dang_Nhap_Activity.this, Dang_Ky_Activity.class);
        startActivity(intent);
    }
}
