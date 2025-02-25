package com.ute.rental.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOAIKHIEUNAI")
public class LoaiKhieuNaiEntity {

    @Id
    @Column(name = "maLoaiKhieuNai")
    private String maLoaiKhieuNai;

    @Column(name = "tenLoaiKhieuNai")
    private String tenLoaiKhieuNai;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @OneToMany(mappedBy = "loaiKhieuNai")
    private List<KhieuNaiEntity> khieuNais = new ArrayList<>();

}
