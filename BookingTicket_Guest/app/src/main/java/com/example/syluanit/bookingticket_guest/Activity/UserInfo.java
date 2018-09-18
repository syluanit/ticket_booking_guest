package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.R;

public class UserInfo extends AppCompatActivity {

    ImageView back, edit;
    Button out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        back = (ImageView) findViewById(R.id.user_info_back_pressed);
        edit = (ImageView) findViewById(R.id.user_info_edit);
        out = (Button) findViewById(R.id.signout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) UserInfo.this).onBackPressed();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
