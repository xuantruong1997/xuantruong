package com.example.fivestar;

import java.io.Serializable;

public class SanPham  implements Serializable {
    private int idsp;
    private String tensp;
    private Integer giasp;
    private String hinhanh;


    public SanPham(int idsp, String tensp, Integer giasp, String hinhanh) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanh = hinhanh;


    }

    public int getId() {
        return idsp;
    }

    public void setId(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

}
