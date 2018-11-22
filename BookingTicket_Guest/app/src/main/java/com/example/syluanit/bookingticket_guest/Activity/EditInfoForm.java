package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditInfoForm extends AppCompatActivity {

    ImageView back;
    Button btn_edit;
    EditText et_name,et_email, et_address, et_doB;
    String gender = "0";
    Database database;
    RadioButton men, women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_form);

        back = (ImageView) findViewById(R.id.back_pressed_editation);
        btn_edit = (Button) findViewById(R.id.btn_editation);
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
                ((Activity) EditInfoForm.this).onBackPressed();
            }
        });


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupSeat_editation) ;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonSeatMen_editation:
                        gender = "1";
                        break;
                    case R.id.radioButtonWomen_editation:
                        gender = "0";
                        break;
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_name.getText().toString();
                String useremail = et_email.getText().toString();
                if (!detect_name(username)) {
                    Toast.makeText(EditInfoForm.this, "Vui lòng kiểm tra tên của bạn!", Toast.LENGTH_LONG).show();
                } else if (!useremail.equals("") && !detect_email(useremail))
                     {
                    Toast.makeText(EditInfoForm.this, "Vui lòng kiểm tra địa chỉ email!", Toast.LENGTH_LONG).show();
                } else {
                final String url = "http://192.168.43.218/busmanager/public/updateUserAndroid";
                sendUserData(url);
                }
            }
        });

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

    private void sendUserData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: " + response.toString());
                        final JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("kq");
                            if (res.equals("1")){
                                Toast.makeText(EditInfoForm.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();

                                String userId = "";
                                String phone = "";
                                Cursor currentUserDB = database.getDaTa("Select * from User");
                                while (currentUserDB.moveToNext()) {
                                    userId = currentUserDB.getString(1);
                                    phone = currentUserDB.getString(7);
                                }
                                String gender1 = "";

                                String doB = "";
                                String[] s = et_doB.getText().toString().split("-");
                                List<String> s1 = Arrays.asList(s);
                                Collections.reverse(s1);
                                doB = TextUtils.join("-", s1);
                                database.queryData("DELETE from User");
                                database.queryData("INSERT INTO User VALUES(null," +
                                        " '"+ userId +"'," +
                                        " '"+ et_name.getText().toString() +"', " +
                                        " '"+ doB +"', " +
                                        " '"+ gender +"', " +
                                        " '"+ et_address.getText().toString() +"', " +
                                        " '"+ et_email.getText().toString() +"', " +
                                        "'"+ phone +"')");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditInfoForm.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                // get userID phrom table User in Sqlite
                String userId = "";
                Cursor currentUserDB = database.getDaTa("Select * from User");
                while (currentUserDB.moveToNext()) {
                    userId = currentUserDB.getString(1);
                }

                String[] s = et_doB.getText().toString().split("-");

                String ten_khong_dau = Normalizer.normalize(et_name.getText().toString(),
                        Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]","");

                params.put("MA", userId);
                params.put("NAME", et_name.getText().toString());
                params.put("NAMEKD", ten_khong_dau);
                params.put("NGAY", s[0]);
                params.put("THANG", s[1]);
                params.put("NAM", s[2]);
                params.put("GT", gender);
                params.put("DIACHI", et_address.getText().toString());
                params.put("EMAIL", et_email.getText().toString());
                Log.d("AAA", "getParams: OK!!!");

                return params;
            }
        };
        requestQueue.add(stringRequest);
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

    public Boolean detect_name (String number) {

        // Regex contains letters and not contain special characters
        if (!number.matches("[a-zA-Z][^#&<>\\\"~;$^%{}?]{1,50}$"))
            return false;

        return true;
    }

    public Boolean detect_email (String number) {

        // Regex matches an entire line
        if (!number.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
            return false;

        return true;
    }

}
