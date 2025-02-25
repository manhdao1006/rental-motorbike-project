package com.ute.rental.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "noiDung")
    private String noiDung;

    @Column(name = "ngayKhieuNai")
    private LocalDateTime ngayKhieuNai;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLoaiKhieuNai")
    private LoaiKhieuNaiEntity loaiKhieuNai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChiTietDonHang")
    private ChiTietDonHangEntity chiTietDonHang;

    @OneToMany(mappedBy = "khieuNai")
    private List<XuLyKhieuNaiEntity> xuLyKhieuNais = new ArrayList<>();

}
