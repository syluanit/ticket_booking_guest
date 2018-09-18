package com.example.syluanit.bookingticket_guest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static  int SPLASH_TIME_OUT =   2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
//        Intent intent = new Intent(this, Home.class);
//        startActivity(intent);
//        finish();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, Home.class);
//                SplashScreen.this.startActivity(intent);
//                SplashScreen.this.finish();
//            }
//        }, SPLASH_TIME_OUT);
        Intent intent = new Intent(SplashScreen.this, Home.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
    }


}
