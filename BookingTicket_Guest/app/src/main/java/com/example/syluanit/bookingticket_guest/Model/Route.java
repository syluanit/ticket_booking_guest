package com.example.syluanit.bookingticket_guest.Model;

public class Route {
    private String startDestination;
    private String endDestination;
    private String day;
    private String timeDep;
    private String timeArr;
    private String price;

    public Route(String startDestination, String endDestination, String day, String timeDep, String timeArr, String price) {
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.day = day;
        this.timeDep = timeDep;
        this.timeArr = timeArr;
        this.price = price;
    }

    public Route(String timeDep, String timeArr, String price) {
        this.timeDep = timeDep;
        this.timeArr = timeArr;
        this.price = price;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
