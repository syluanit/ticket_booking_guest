package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.R;

public class Fill_Customer_Info_Form extends AppCompatActivity {

    Button next;
    ImageView back;
    private TextView name, email, phone, phone2, idCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill__custom__info);

        back = (ImageView) findViewById(R.id.info_back_pressed);
        next = (Button)  findViewById(R.id.btn_fill_info_next);
        name = (TextView)  findViewById(R.id.name);
        email = (TextView)  findViewById(R.id.email);
        phone = (TextView)  findViewById(R.id.phone);
        phone2 = (TextView)  findViewById(R.id.phone2);
        idCard = (TextView)  findViewById(R.id.IDCard);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Fill_Customer_Info_Form.this).onBackPressed();
                Fragment_Tang_Tren.adapter.notifyDataSetChanged();
                Fragment_Tang_Duoi.adapter.notifyDataSetChanged();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString();
                String useremail = email.getText().toString();
                String userphone = phone.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng nhập tên của bạn!", Toast.LENGTH_LONG).show();
                } else if (useremail.isEmpty()) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng nhập địa chỉ email!", Toast.LENGTH_LONG).show();
                } else if (userphone.isEmpty()) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_LONG).show();
                } else {
                    Home.currentUser.setName(username);
                    Home.currentUser.setEmail(useremail);
                    Home.currentUser.setPhoneNumber(userphone);

                    Intent intent = new Intent(Fill_Customer_Info_Form.this, TicketBookingInfo.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment_Tang_Tren.adapter.notifyDataSetChanged();
        Fragment_Tang_Duoi.adapter.notifyDataSetChanged();
    }
}
