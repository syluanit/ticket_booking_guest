package com.example.syluanit.bookingticket_guest.Model;

public class TicketInfo {
    private String type;
    private String content;

    public TicketInfo(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
