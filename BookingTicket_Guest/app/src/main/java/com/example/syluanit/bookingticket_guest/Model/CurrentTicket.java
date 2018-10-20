package com.example.syluanit.bookingticket_guest.Model;

import java.util.ArrayList;

public class CurrentTicket {
    private String Id;
    private String startDestination;
    private String endDestination;
    private String day;
    private String timeDep;
    private String timeArr;
    private ArrayList<String> seat;
    private String price;
    private int numSeat;
    private int typeSeat;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public int getTypeSeat() {
        return typeSeat;
    }

    public void setTypeSeat(int typeSeat) {
        this.typeSeat = typeSeat;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeDep() {
        return timeDep;
    }

    public void setTimeDep(String timeDep) {
        this.timeDep = timeDep;
    }

    public String getTimeArr() {
        return timeArr;
    }

    public void setTimeArr(String timeArr) {
        this.timeArr = timeArr;
    }

    public ArrayList<String> getSeat() {
        return seat;
    }

    public void setSeat(ArrayList<String> seat) {
        this.seat = seat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(int numSeat) {
        this.numSeat = numSeat;
    }
}
