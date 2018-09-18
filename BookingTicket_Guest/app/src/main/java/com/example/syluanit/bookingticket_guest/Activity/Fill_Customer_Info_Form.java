package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.R;

public class Fill_Customer_Info_Form extends AppCompatActivity {

    Button next;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill__custom__info);

        back = (ImageView) findViewById(R.id.info_back_pressed);
        next = (Button)  findViewById(R.id.btn_fill_info_next);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Fill_Customer_Info_Form.this).onBackPressed();
//                Fragment_Tang_Tren.adapter.notifyDataSetChanged();
//                Fragment_Tang_Duoi.adapter.notifyDataSetChanged();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fill_Customer_Info_Form.this, TicketBookingInfo.class);
                startActivity(intent);
            }
        });
    }
}
