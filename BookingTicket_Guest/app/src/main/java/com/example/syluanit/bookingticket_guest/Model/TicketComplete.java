package com.example.syluanit.bookingticket_guest.Model;

public class TicketComplete {
    private int id;
    private String [] seat;
    private int makh;
    private int dodai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getSeat() {
        return seat;
    }

    public void setSeat(String[] seat) {
        this.seat = seat;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public int getDodai() {
        return dodai;
    }

    public void setDodai(int dodai) {
        this.dodai = dodai;
    }
}
