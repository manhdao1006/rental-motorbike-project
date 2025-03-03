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
@Table(name = "DONHANG")
public class DonHangEntity {

    @Id
    @Column(name = "maDonHang")
    private String maDonHang;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao;

    @Column(name = "ngayGiaoXe")
    private LocalDateTime ngayGiaoXe;

    @Column(name = "ngayTraXe")
    private LocalDateTime ngayTraXe;

    @Column(name = "ngayThanhToan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "hoTenNguoiGiaoHang")
    private String hoTenNguoiGiaoHang;

    @Column(name = "soDienThoaiGiaoHang")
    private String soDienThoaiGiaoHang;

    @Column(name = "trangThaiDonHang")
    private String trangThaiDonHang;

    @Column(name = "phuongThucThanhToan")
    private String phuongThucThanhToan;

    @Column(name = "lyDoHuy")
    private String lyDoHuy;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa = "1";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKhachHang")
    private KhachHangEntity khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChuCuaHang")
    private ChuCuaHangEntity chuCuaHang;

    @OneToMany(mappedBy = "donHang")
    private List<ChiTietDonHangEntity> chiTietDonHangs = new ArrayList<>();

}
