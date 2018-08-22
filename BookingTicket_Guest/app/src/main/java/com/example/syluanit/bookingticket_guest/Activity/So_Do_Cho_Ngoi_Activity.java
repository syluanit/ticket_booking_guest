package com.example.syluanit.bookingticket_guest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.syluanit.bookingticket_guest.Adapter.ViewPagerAdapter;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Duoi;
import com.example.syluanit.bookingticket_guest.Fragment.Fragment_Tang_Tren;
import com.example.syluanit.bookingticket_guest.R;

public class So_Do_Cho_Ngoi_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Button btn_Dat_Ve, btn_Goi_Y_Cho_Ngoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so__do__cho__ngoi_);

        tabLayout =  findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        btn_Dat_Ve = findViewById(R.id.btn_dat_ve);
        btn_Goi_Y_Cho_Ngoi = findViewById(R.id.btn_goi_y_cho_ngoi);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Fragment_Tang_Tren(),"Tầng Trên");
        viewPagerAdapter.addFragment(new Fragment_Tang_Duoi(),"Tầng Dưới");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btn_Dat_Ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(So_Do_Cho_Ngoi_Activity.this, Dang_Nhap_Activity.class);
                startActivity(intent);
            }
        });
    }
}
