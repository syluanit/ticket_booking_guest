package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.R;

public class UserInfo extends AppCompatActivity {

    ImageView back, edit;
    Button out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        back = (ImageView) findViewById(R.id.user_info_back_pressed);
        edit = (ImageView) findViewById(R.id.user_info_edit);
        out = (Button) findViewById(R.id.signout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) UserInfo.this).onBackPressed();
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
                        Intent intent = new Intent(UserInfo.this, EditInfoForm.class);
                        startActivity(intent);
                    }
                });

                tvPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

            }
        });
    }
}
