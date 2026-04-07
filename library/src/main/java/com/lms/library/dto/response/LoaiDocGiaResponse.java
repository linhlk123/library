package com.lms.library.dto.response;

public class LoaiDocGiaResponse {

    private Integer maLoaiDocGia;
    private String tenLoaiDocGia;

    public LoaiDocGiaResponse() {
    }

    public LoaiDocGiaResponse(Integer maLoaiDocGia, String tenLoaiDocGia) {
        this.maLoaiDocGia = maLoaiDocGia;
        this.tenLoaiDocGia = tenLoaiDocGia;
    }

    public Integer getMaLoaiDocGia() {
        return maLoaiDocGia;
    }

    public void setMaLoaiDocGia(Integer maLoaiDocGia) {
        this.maLoaiDocGia = maLoaiDocGia;
    }

    public String getTenLoaiDocGia() {
        return tenLoaiDocGia;
    }

    public void setTenLoaiDocGia(String tenLoaiDocGia) {
        this.tenLoaiDocGia = tenLoaiDocGia;
    }
}