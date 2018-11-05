package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.syluanit.bookingticket_guest.Service.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dang_Nhap_Activity extends AppCompatActivity {

    ImageView back;
    Button login;
    EditText username, password;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__nhap_);

        back = (ImageView) findViewById(R.id.sign_in_back_pressed);
        login = (Button) findViewById(R.id.btn_Login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passWord);

        database = new Database(this, "ticket.sqlite", null, 1);

//        username.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_location_city_black_24dp,0,0,0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Dang_Nhap_Activity.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
              }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after login successfully
//                Home.navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
//                Home.navigationView.getMenu().findItem(R.id.nav_BookingHistory).setVisible(true);
//                String url = "http://192.168.40.68/laravel/receiveDataUser";
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")) {
                    Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                } else if (!detect_number(username.getText().toString())) {
                    Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng nhập số điện thoại đúng!", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 6 || pass.length() > 30) {
                    Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng nhập mật khẩu đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://192.168.43.218/busmanager/public/dangnhapAndroid";
                    sendUserData(url, user);
                }
            }
        });
    }

    private void sendUserData(String url, final String user){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAA", "onResponse: yeahyeah" + response);
                        try {
                            final JSONObject jsonObject =   new JSONObject(response);
                            String res = jsonObject.getString("kq");

                            if (res.equals("success")){
                                final Dialog dialog = new Dialog(Dang_Nhap_Activity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_outday);
                                TextView tv_annoucement = dialog.findViewById(R.id.tv_OK_outday);
                                TextView content = dialog.findViewById(R.id.tv_content);
                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                content.setText("Đăng nhập thành công");
//                                Toast.makeText(Dang_Nhap_Activity.this,response.toString() + "", Toast.LENGTH_SHORT).show();
                                tv_annoucement.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        try {
                                            JSONArray jsonArray = jsonObject.getJSONArray("tt");
                                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                                            String userId = jsonObject1.getString("Mã");
                                            database.queryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    " userId INTEGER, name VARCHAR(200), doB VARCHAR(200), " +
                                                    "gender VARCHAR(200), address VARCHAR(200), " +
                                                    "email VARCHAR(200), phoneNumber VARCHAR(200))");

                                            database.queryData("INSERT INTO User VALUES(null," +
                                                    " '"+ userId +"'," +
                                                    " '"+ jsonObject1.getString("Tên") +"', " +
                                                    " '"+ jsonObject1.getString("Ngày_sinh") +"', " +
                                                    " '"+ jsonObject1.getString("Giới tính") +"', " +
                                                    " '"+ jsonObject1.getString("Địa chỉ") +"', " +
                                                    " '"+ jsonObject1.getString("Email") +"', " +
                                                    "'"+user+"')");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (!Home.routeSignal){
                                        Intent intent = new Intent(Dang_Nhap_Activity.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                        }
                                        else {
                                            Intent intent = getIntent();
                                            String res  = intent.getStringExtra("ticketMap");
                                            Intent i = new Intent(Dang_Nhap_Activity.this, So_Do_Cho_Ngoi_Activity.class);
                                            i.putExtra("ticketMap", res);
                                            startActivity(i);
                                            finish();

                                            Home.navigationView.getMenu().findItem(R.id.nav_Login_SignUp).setVisible(false);
                                            Home.navigationView.getMenu().findItem(R.id.nav_SignUp).setVisible(false);
                                            Home.navigationView.getMenu().findItem(R.id.nav_TravelHistory).setVisible(true);
                                            Home.navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
                                            Home.navigationView.getMenu().findItem(R.id.nav_user_menu).setVisible(true);

                                            Cursor currentUserDB = database.getDaTa("Select * from User");
                                            while (currentUserDB.moveToNext()) {
                                                TextView tv = (TextView) Home.navigationView.getHeaderView(0).findViewById(R.id.nav_username);
                                                tv.setText(currentUserDB.getString(7));
                                            }
                                        }
                                    }
                                });
                                dialog.show();
                            }
                            else if (res.equals("wrong")){
                                Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng kiểm tra tài khoản mật khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dang_Nhap_Activity.this, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("X-CSRF-Token", accessToken);
                params.put("DNDT", username.getText().toString().trim());
                params.put("DNMK", password.getText().toString().trim());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }

    //Validation
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
}
