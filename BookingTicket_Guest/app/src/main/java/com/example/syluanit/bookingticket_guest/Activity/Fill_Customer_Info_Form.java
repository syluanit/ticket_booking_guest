package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.syluanit.bookingticket_guest.Service.Database;

public class Fill_Customer_Info_Form extends AppCompatActivity {

    Button next;
    ImageView back;
    private TextView name, email, phone, phone2, idCard;
    Database database;
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
                if (Home.currentTicket.getTypeSeat() == 1) {
                    Fragment_Tang_Tren.adapter.notifyDataSetChanged();
                    Fragment_Tang_Duoi.adapter.notifyDataSetChanged();
                } else {
                    So_Do_Cho_Ngoi_Activity.adapter.notifyDataSetChanged();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString();
                String useremail = email.getText().toString();
                String userphone = phone.getText().toString();

                if (username.isEmpty() || !detect_name(username)) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng kiểm tra tên của bạn!", Toast.LENGTH_LONG).show();
                } else if (useremail.isEmpty() || !detect_email(useremail)) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng kiểm tra địa chỉ email!", Toast.LENGTH_LONG).show();
                } else if (userphone.isEmpty() | !detect_number(userphone)) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("user",username);
                    bundle.putString("email",useremail);
                    bundle.putString("phone",userphone);
                    Intent intent = new Intent(Fill_Customer_Info_Form.this, TicketBookingInfo.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        database = new Database(this, "ticket.sqlite", null, 1);

        Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");

        if (data.getCount() > 0){
            Cursor currentUserDB = database.getDaTa("Select * from User");
            while (currentUserDB.moveToNext()) {
                name.setText(currentUserDB.getString(2));
                email.setText(currentUserDB.getString(6));
                phone.setText(currentUserDB.getString(7));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Home.currentTicket.getTypeSeat() == 1) {
            Fragment_Tang_Tren.adapter.notifyDataSetChanged();
            Fragment_Tang_Duoi.adapter.notifyDataSetChanged();
        } else {
            So_Do_Cho_Ngoi_Activity.adapter.notifyDataSetChanged();
        }
    }

    public Boolean detect_number (String number) {
        number = number.replace("\"", "");
        number = number.replace("-", "");
        number = number.trim();
        number = number.replaceAll("\\.", "");
        // $number is not a phone number
        if (!number.matches("(0[3578]|09)[0-9]{8}"))
            return false;

        // if not found, return false
        return true;
    }

    public Boolean detect_name (String number) {

        if (!number.matches("[a-zA-Z][^#&<>\\\"~;$^%{}?]{1,50}$"))
            return false;

        return true;
    }

    public Boolean detect_email (String number) {

        if (!number.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
            return false;

        // if not found, return false
        return true;
    }
}
