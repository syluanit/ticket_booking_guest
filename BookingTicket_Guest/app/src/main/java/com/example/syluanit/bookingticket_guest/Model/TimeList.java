package com.example.syluanit.bookingticket_guest.Model;

public class TimeList {
    private String time;
    private int icon;

    public TimeList(String time, int icon) {
        this.time = time;
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
