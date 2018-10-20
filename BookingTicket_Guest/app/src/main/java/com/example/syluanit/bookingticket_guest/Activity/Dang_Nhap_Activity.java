package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Dang_Nhap_Activity extends AppCompatActivity {

    ImageView back;
    Button login;
    TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__nhap_);

        back = (ImageView) findViewById(R.id.sign_in_back_pressed);
        login = (Button) findViewById(R.id.btn_Login);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.passWord);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Dang_Nhap_Activity.this).onBackPressed();
              }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after login successfully
//                Home.navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
//                Home.navigationView.getMenu().findItem(R.id.nav_BookingHistory).setVisible(true);
//                String url = "http://192.168.40.68/laravel/receiveDataUser";
                String url = "http://192.168.1.214/laravel/receiveDataUser";
                sendUserData(url);

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
                        if (response.toString().trim().equals("success")){
                            Toast.makeText(Dang_Nhap_Activity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.toString().trim().equals("fail")){
                            Toast.makeText(Dang_Nhap_Activity.this, "that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dang_Nhap_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
//                params.put("X-CSRF-Token", accessToken);
                params.put("email", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                Log.d("AAA", "getParams: OK!!!");
                return params;
            }
        };
            requestQueue.add(stringRequest);
    }

    public void quenMatKhau(View v){
        Intent intent = new Intent(this, Lay_Lai_Mat_Khau.class);
        startActivity(intent);
    }
    public void dangKy(View v){
        Intent intent = new Intent(Dang_Nhap_Activity.this, Dang_Ky_Activity.class);
        startActivity(intent);
    }
}
