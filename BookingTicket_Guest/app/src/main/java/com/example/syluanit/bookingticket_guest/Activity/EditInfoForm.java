package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.ChangePasswordForm;
import com.example.syluanit.bookingticket_guest.R;

public class EditInfoForm extends AppCompatActivity {

    ImageView back;
    Button btn_edit;
    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_form);

        back = (ImageView) findViewById(R.id.back_pressed_editation);
        btn_edit = (Button) findViewById(R.id.btn_editation);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) EditInfoForm.this).onBackPressed();
            }
        });
    }
}
