package com.example.fivestar;

import java.io.Serializable;

public class GioHang implements Serializable {
    private  int idgiohang;
    private  int idsp;
    private  int sl;
    private String tensp;
    private String hinhanh;
    private int giasp;

    public GioHang(int idgiohang, int idsp, int sl, String tensp, String hinhanh, int giasp) {
        this.idgiohang = idgiohang;
        this.idsp = idsp;
        this.sl = sl;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.giasp = giasp;
    }

    public int getIdgiohang() {
        return idgiohang;
    }

    public void setIdgiohang(int idgiohang) {
        this.idgiohang = idgiohang;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }
}
