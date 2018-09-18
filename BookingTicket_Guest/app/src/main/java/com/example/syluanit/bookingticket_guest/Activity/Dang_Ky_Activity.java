package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.R;

public class Dang_Ky_Activity extends AppCompatActivity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__ky_);

        back = (ImageView) findViewById(R.id.sign_up_back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Dang_Ky_Activity.this).onBackPressed();
            }
        });
    }
}
