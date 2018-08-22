package com.example.syluanit.bookingticket_guest.Model;

public class GheNgoi {

    private int hinhAnh;
    private String viTri;
    private int trangThai;

    public GheNgoi(int hinhAnh, String viTri) {
        this.hinhAnh = hinhAnh;
        this.viTri = viTri;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
}
