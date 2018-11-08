package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Dang_Ky_Activity extends AppCompatActivity {

    ImageView back;
    Button signUp;
    EditText username, password, rePassword, realname, dob;
    String gender = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__ky_);

        back = (ImageView) findViewById(R.id.sign_up_back_pressed);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passWord);
        rePassword = (EditText) findViewById(R.id.repassWord);
        rePassword = (EditText) findViewById(R.id.repassWord);
        realname = (EditText) findViewById(R.id.name_signup);
        dob = (EditText) findViewById(R.id.dob_signup);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();
                String name = realname.getText().toString();
                String dob = realname.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")||name.equals("")||dob.equals("")) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!detect_number(user)) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập số điện thoại đúng!", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6 || pass.length() > 30) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập mật khẩu từ 6 đến 30 ký tự!", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(repass)) {
                    Toast.makeText(Dang_Ky_Activity.this, "Mật khẩu chưa khớp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                }  else if (!detect_name(name)) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập tên chỉ chứa các ký tự!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = "http://192.168.43.218/busmanager/public/dangkyAndroid";
                    sendUserData(url);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Dang_Ky_Activity.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
            }
        });

        dob.setFocusable(false);
        dob.setClickable(true);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDay();
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupSeat_signup) ;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonSeatMen_signup:
                        gender = "1";
                        break;
                    case R.id.radioButtonWomen_signup:
                        gender = "0";
                        break;
                }
            }
        });

    }

    private void sendUserData(String url){

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: yeahyeah");
//                        Toast.makeText(Dang_Ky_Activity.this, response + "", Toast.LENGTH_SHORT).show();
                        if (response.toString().trim().equals("\"1\"")){
                            final Dialog dialog = new Dialog(Dang_Ky_Activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_outday);
                            TextView tv_annoucement = dialog.findViewById(R.id.tv_OK_outday);
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            TextView content = dialog.findViewById(R.id.tv_content);
                            content.setText("Đăng ký thành công");
                            tv_annoucement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Dang_Ky_Activity.this, Home.class);
                                    startActivity(intent);
                                }
                            });
                            dialog.show();
                        }
                        else if (response.toString().trim().equals("\"0\"")){
                            Toast.makeText(Dang_Ky_Activity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dang_Ky_Activity.this, "Vui lòng kiểm tra kết nối sau đó thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("X-CSRF-Token", accessToken);
                params.put("SDT", username.getText().toString().trim());
                params.put("MK", password.getText().toString().trim());
                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void pickDay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay, thang, nam = 0;
        if (dob.getText().toString().matches("")) {
            ngay = calendar.get(Calendar.DATE);
            thang = calendar.get(Calendar.MONTH);
            nam = calendar.get(Calendar.YEAR);
        }
        else {
            String s = dob.getText().toString();
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
                dob.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang , ngay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public Boolean detect_number (String number) {
        number  = number.replace("\"", "");
        number = number.replace("-","");
        number = number.trim();
        number = number.replaceAll("\\.","");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
