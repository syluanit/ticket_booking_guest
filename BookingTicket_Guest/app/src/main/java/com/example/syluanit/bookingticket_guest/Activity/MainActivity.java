package com.example.syluanit.bookingticket_guest.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.syluanit.bookingticket_guest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText et_pickDay,et_from, et_to;
    Button btn_ticketSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        et_pickDay = (EditText) findViewById(R.id.pickDay);
        et_from = (EditText) findViewById(R.id.et_from);
        et_to = (EditText) findViewById(R.id.et_to);
        btn_ticketSearch = (Button) findViewById(R.id.btn_ticketSearch);
        // TODO Hiding soft keyboard on editext
        et_pickDay.setInputType(InputType.TYPE_NULL);

        et_pickDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pickDay();
            }
        });


        btn_ticketSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = et_from.getText().toString();
                String to = et_to.getText().toString();
                String date = et_pickDay.getText().toString();
                // TODO Checking infomation
//                if (from.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn điểm đi!", Toast.LENGTH_LONG).show();
//                } else if (to.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn điểm đến!", Toast.LENGTH_LONG).show();
//                } else if (date.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Vui lòng chọn ngày đi!", Toast.LENGTH_LONG).show();
//                } else {
                    // TODO Sending data to the TimeList Activity using Bundle
                    Intent intent = new Intent(MainActivity.this, TimeListActivity.class);
                    Bundle ticket = new Bundle();
                    ticket.putString("from", from);
                    ticket.putString("to", to);
                    ticket.putString("date", date);
                    intent.putExtras(ticket);
                    startActivity(intent);
//                }
            }
        });
    }

    private void pickDay(){
        final Calendar calendar =  Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month,dayOfMonth);
                // TODO Time format and set picked day to the edittext
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                et_pickDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);

        datePickerDialog.show();
    }

}
