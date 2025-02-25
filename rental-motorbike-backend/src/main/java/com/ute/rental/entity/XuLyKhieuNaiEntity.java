package com.ute.rental.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "XULYKHIEUNAI")
public class XuLyKhieuNaiEntity {

    @Id
    @Column(name = "maXuLyKhieuNai")
    private String maXuLyKhieuNai;

    @Column(name = "noiDung")
    private String noiDung;

    @Column(name = "ngayXuLy")
    private LocalDateTime ngayXuLy;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKhieuNai")
    private KhieuNaiEntity khieuNai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhanVien")
    private NhanVienEntity nhanVien;

}
