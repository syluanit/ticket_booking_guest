package com.example.syluanit.bookingticket_guest.Service;



import android.app.Application;

import com.example.syluanit.bookingticket_guest.Service.FontOverride;

/**
 * Created by vamsi on 06-05-2017 for android custom font article
 */

public class Customfontapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontOverride.setDefaultFont(this, "DEFAULT", "fonts/robusta.ttf");
        FontOverride.setDefaultFont(this, "MONOSPACE", "fonts/robusta.ttf");
        FontOverride.setDefaultFont(this, "SERIF", "fonts/robusta.ttf");
        FontOverride.setDefaultFont(this, "SANS_SERIF", "fonts/robusta.ttf");
    }
}