package com.example.fivestar;

public class DonHang {
    private int iddonhang;
    private int tongtien;
    private String tensp;
    private String diachi;

    public DonHang(int iddonhang, int tongtien, String tensp, String diachi) {
        this.iddonhang = iddonhang;
        this.tongtien = tongtien;
        this.tensp = tensp;
        this.diachi = diachi;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
