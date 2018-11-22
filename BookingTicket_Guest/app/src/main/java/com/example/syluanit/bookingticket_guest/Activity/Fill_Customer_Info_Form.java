package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Fill_Customer_Info_Form extends AppCompatActivity {

    Button next;
    ImageView back;
    EditText et_name,et_email, et_address, et_doB;
    Database database;
    RadioButton men, women;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill__custom__info);

        back = (ImageView) findViewById(R.id.info_back_pressed);
        next = (Button)  findViewById(R.id.btn_fill_info_next);
        et_name = (EditText) findViewById(R.id.tv_Name_editation);
        et_email = (EditText) findViewById(R.id.tv_Email_editation);
        et_address = (EditText) findViewById(R.id.tv_Address_editation);
        et_doB = (EditText) findViewById(R.id.pickDay_editation);
        men = (RadioButton) findViewById(R.id.radioButtonSeatMen_editation);
        women = (RadioButton) findViewById(R.id.radioButtonWomen_editation);

        database = new Database(this, "ticket.sqlite", null, 1);

        prepareTicketInfo();

        et_doB.setFocusable(false);
        et_doB.setClickable(true);
        et_doB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDay();
            }
        });

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

                String username = et_name.getText().toString();
                String useremail = et_email.getText().toString();
                String userphone = et_address.getText().toString();

                if (username.isEmpty() || !detect_name(username)) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng kiểm tra tên của bạn!", Toast.LENGTH_LONG).show();
                } else if (!useremail.equals("") && !detect_email(useremail)) {
                    Toast.makeText(Fill_Customer_Info_Form.this, "Vui lòng kiểm tra địa chỉ email!", Toast.LENGTH_LONG).show();
                } else   {
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

//        database = new Database(this, "ticket.sqlite", null, 1);
//
//        Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");
//
//        if (data.getCount() > 0){
//            Cursor currentUserDB = database.getDaTa("Select * from User");
//            while (currentUserDB.moveToNext()) {
//                name.setText(currentUserDB.getString(2));
//                email.setText(currentUserDB.getString(6));
//                phone.setText(currentUserDB.getString(7));
//            }
//        }
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

    private void pickDay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay, thang, nam = 0;
        if (et_doB.getText().toString().matches("")) {
            ngay = calendar.get(Calendar.DATE);
            thang = calendar.get(Calendar.MONTH);
            nam = calendar.get(Calendar.YEAR);
        }
        else {
            String s = et_doB.getText().toString();
            String [] arrayString = s.split("-");
            ngay = Integer.parseInt(arrayString[0]);
            thang = Integer.parseInt(arrayString[1]);
            nam = Integer.parseInt(arrayString[2]);
        }

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month,dayOfMonth);
                // TODO Time format and set picked day to the edittext

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                et_doB.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang , ngay);
        datePickerDialog.show();
    }

    private void prepareTicketInfo() {
        Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");

        if (data.getCount() > 0) {
            Cursor currentUserDB = database.getDaTa("Select * from User");
            while (currentUserDB.moveToNext()) {
                String username = currentUserDB.getString(2);
                String doB = currentUserDB.getString(3);
                String gender1 = currentUserDB.getString(4);
                String address = currentUserDB.getString(5);
                String email = currentUserDB.getString(6);
                String phone = currentUserDB.getString(7);
                if (gender1.equals("1")) {
                    men.setChecked(true);
                    gender = "1";
                } else {
                    women.setChecked(true);
                    gender = "0";
                }
                String[] s = doB.split("-");
                List<String> s1 = Arrays.asList(s);
                Collections.reverse(s1);
                doB = TextUtils.join("-", s1);

                if (username.equals("null")) {
                    username = "";
                }
                if (doB.equals("null")) {
                    doB = "";
                }
                if (gender.equals("null")) {
                    gender = "";
                }
                if (email.equals("null")) {
                    email = "";
                }
                if (address.equals("null")) {
                    address = "";
                }


                et_name.setText(username);
                et_email.setText(email);
                et_address.setText(address);
                et_doB.setText(doB);

            }
        }
    }
}
