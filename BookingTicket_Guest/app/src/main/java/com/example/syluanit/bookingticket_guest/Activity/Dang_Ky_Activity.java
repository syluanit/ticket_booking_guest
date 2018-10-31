package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class Dang_Ky_Activity extends AppCompatActivity {

    ImageView back;
    Button signUp;
    EditText username, password, rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__ky_);

        back = (ImageView) findViewById(R.id.sign_up_back_pressed);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passWord);
        rePassword = (EditText) findViewById(R.id.repassWord);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!detect_number(user)) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập số điện thoại đúng!", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6 || pass.length() > 30) {
                    Toast.makeText(Dang_Ky_Activity.this, "Vui lòng nhập mật khẩu đúng!", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(repass)) {
                    Toast.makeText(Dang_Ky_Activity.this, "Mật khẩu chưa khớp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                }else {
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
                        Toast.makeText(Dang_Ky_Activity.this, "Error", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
