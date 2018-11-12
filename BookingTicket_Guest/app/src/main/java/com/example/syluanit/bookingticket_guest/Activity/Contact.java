package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.R;

public class Contact extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        back = (ImageView) findViewById(R.id.back_pressed_contact);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Contact.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
