package com.lms.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LOAIDOCGIA")
public class LoaiDocGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLoaiDocGia")
    private Integer maLoaiDocGia;

    @Column(name = "TenLoaiDocGia", nullable = false, length = 100)
    private String tenLoaiDocGia;

    public LoaiDocGia() {
    }

    public LoaiDocGia(Integer maLoaiDocGia, String tenLoaiDocGia) {
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