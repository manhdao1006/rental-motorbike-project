package com.ute.rental.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KHIEUNAI")
public class KhieuNaiEntity {

    @Id
    @Column(name = "maKhieuNai")
    private String maKhieuNai;

    @Column(name = "noiDungKhieuNai")
    private String noiDungKhieuNai;

    @Column(name = "ngayKhieuNai")
    private LocalDateTime ngayKhieuNai;

    @Column(name = "noiDungPhanHoi")
    private String noiDungPhanHoi;

    @Column(name = "ngayPhanHoi")
    private LocalDateTime ngayPhanHoi;

    @Column(name = "trangThaiXuLy")
    private String trangThaiXuLy;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa = "1";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLoaiKhieuNai")
    private LoaiKhieuNaiEntity loaiKhieuNai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonHang")
    private DonHangEntity donHang;

    @OneToMany(mappedBy = "khieuNai", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<AnhKhieuNaiEntity> anhKhieuNais = new ArrayList<>();

}
