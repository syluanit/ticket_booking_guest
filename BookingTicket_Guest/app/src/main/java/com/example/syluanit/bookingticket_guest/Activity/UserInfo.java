package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Adapter.Ticket_Info_Adapter;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;
import com.example.syluanit.bookingticket_guest.Service.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    ImageView back, edit;
    Button out;
    RecyclerView rv_ticket;
    Ticket_Info_Adapter adapter;
    ArrayList<TicketInfo> ticketInfoArrayList;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        back = (ImageView) findViewById(R.id.user_info_back_pressed);
        edit = (ImageView) findViewById(R.id.user_info_edit);
        out = (Button) findViewById(R.id.signout);
        rv_ticket = (RecyclerView) findViewById(R.id.rv_ticketInfo);
        rv_ticket.setHasFixedSize(true);
        ticketInfoArrayList = new ArrayList<>();

        database = new Database(this, "ticket.sqlite", null, 1);

        adapter = new Ticket_Info_Adapter(this, ticketInfoArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_ticket.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rv_ticket.addItemDecoration(dividerItemDecoration);
        rv_ticket.setAdapter(adapter);
        prepareTicketInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) UserInfo.this).onBackPressed();
                Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserInfo.this);
                dialog.setContentView(R.layout.dialog_edit);
                TextView tvEditInfo = (TextView) dialog.findViewById(R.id.edit_thong_tin);
                TextView tvPassword = (TextView) dialog.findViewById(R.id.change_password);
                TextView tvBack = (TextView) dialog.findViewById(R.id.back_edit);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                tvEditInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Intent intent = new Intent(UserInfo.this, EditInfoForm.class);
                        startActivity(intent);

                    }
                });

                tvPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Intent intent = new Intent(UserInfo.this, ChangePasswordForm.class);
                        startActivity(intent);

                    }
                });
                dialog.show();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserInfo.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_exit);
                TextView content = dialog.findViewById(R.id.content);
                Button btn_exit = dialog.findViewById(R.id.btn_cancel);
                Button btn_accept = dialog.findViewById(R.id.btn_accept);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                content.setText("Bạn có muốn đăng xuất?");
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.queryData("Drop table IF exists User");
                        Intent intent = new Intent(UserInfo.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        });
    }

    private void prepareTicketInfo() {
        Cursor data = database.getDaTa("SELECT * FROM sqlite_master WHERE name ='User' and type='table'");

        if (data.getCount() > 0) {
            Cursor currentUserDB = database.getDaTa("Select * from User");
            while (currentUserDB.moveToNext()) {
                String username = currentUserDB.getString(2);
                String doB = currentUserDB.getString(3);
                String gender = currentUserDB.getString(4);
                String address = currentUserDB.getString(5);
                String email = currentUserDB.getString(6);
                String phone = currentUserDB.getString(7);
                if (gender.equals("1")) {
                    gender = "Nam";
                } else gender = "Nữ";
                String[] s = doB.split("-");
                List<String> s1 = Arrays.asList(s);
                Collections.reverse(s1);
                doB = TextUtils.join("-", s1);
//                for (int i = 0; i < s.length; i++){
//
//                }
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


                ticketInfoArrayList.add(new TicketInfo("Họ và tên", username));
                ticketInfoArrayList.add(new TicketInfo("Email", email));
                ticketInfoArrayList.add(new TicketInfo("Điện thoại", phone));
                ticketInfoArrayList.add(new TicketInfo("Địa chỉ", address));
                ticketInfoArrayList.add(new TicketInfo("Ngày sinh", doB));
                ticketInfoArrayList.add(new TicketInfo("Giới tính", gender));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.navigationView.getMenu().findItem(R.id.nav_Booking).setChecked(true);
    }
}
