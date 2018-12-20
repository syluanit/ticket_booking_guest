
package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangePasswordForm extends AppCompatActivity {

    EditText password, newPassword, confirmPassword;
    Button accept;
    ImageView back;
    Database database;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_form);

        back = (ImageView) findViewById(R.id.back_pressed_password);
        accept = (Button) findViewById(R.id.btn_password);
        password = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.check_new_password);

        database = new Database(this, "ticket.sqlite", null, 1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) ChangePasswordForm.this).onBackPressed();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String newPass = newPassword.getText().toString();
                String conPass = confirmPassword.getText().toString();
                if (pass.equals("") || newPass.equals("") || conPass.equals("")) {
                    Toast.makeText(ChangePasswordForm.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if (pass.length() < 6 || pass.length() > 30 || newPass.length() < 6 || newPass.length() > 30 ||
                        conPass.length() < 6 || conPass.length() > 30 ) {
                    Toast.makeText(ChangePasswordForm.this, "Vui lòng nhập mật khẩu từ 6 đến 30 ký tự!", Toast.LENGTH_SHORT).show();
                } else if (newPass.equals(conPass)) {
                    String ip = getResources().getString(R.string.ip);
                    String address = getResources().getString(R.string.address);
                    url = ip + address + "/changePassAndroid";
//                    final String url = "http://192.168.43.218/busmanager/public/changePassAndroid";
                    sendUserData(url);
                } else {
                    Toast.makeText(ChangePasswordForm.this, "Mật khẩu chưa khớp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
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
                        Log.d("AAA", "onResponse: " + response.toString());
                        final JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("kq");
                            if (res.equals("1")){
                                Toast.makeText(ChangePasswordForm.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ChangePasswordForm.this, "Vui lòng nhập đúng mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePasswordForm.this, "Vui lòng kiểm tra kết nối mạng hoặc thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
//                params.put("X-CSRF-Token", accessToken);
                String userId = "";
                Cursor currentUserDB = database.getDaTa("Select * from User");
                while (currentUserDB.moveToNext()) {
                    userId = currentUserDB.getString(1);
                }
                params.put("MA", userId);
                params.put("MKCU", password.getText().toString());
                params.put("MKMOI", newPassword.getText().toString());

                Log.d("AAA", "getParams: OK!!!");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
