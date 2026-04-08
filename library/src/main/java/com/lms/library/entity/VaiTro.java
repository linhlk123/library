package com.lms.library.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class VaiTro {
    @Id
    String tenVaiTro;

    String moTaVaiTro;

    @ManyToMany
    @JoinTable(
        name = "phanquyen_vaitro",
        joinColumns = @JoinColumn(name = "tenVaiTro", referencedColumnName = "tenVaiTro"),
        inverseJoinColumns = @JoinColumn(name = "tenQuyen", referencedColumnName = "tenQuyen")
    )
    Set<PhanQuyen> danhSachPhanQuyen;
}
