package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.R;

public class Lay_Lai_Mat_Khau extends AppCompatActivity {

    ImageView back;
    Button btn_password;
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay__lai__mat__khau);

        et_email = (EditText) findViewById(R.id.et_email_forget);
        btn_password = (Button) findViewById(R.id.btn_password_forget);
        back = (ImageView) findViewById(R.id.password_back_pressed);

        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Lay_Lai_Mat_Khau.this).onBackPressed();
            }
        });
    }

}
