package com.ute.rental.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ute.rental.entity.impl.MaChiTietDonHang;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHITIETDONHANG")
public class ChiTietDonHangEntity {

    @EmbeddedId
    private MaChiTietDonHang maChiTietDonHang;

    @ManyToOne
    @MapsId("maDonHang")
    @JoinColumn(name = "maDonHang")
    private DonHangEntity donHang;

    @ManyToOne
    @MapsId("maXeMay")
    @JoinColumn(name = "maXeMay")
    private XeMayEntity xeMay;

    @Column(name = "giaThue")
    private BigDecimal giaThue;

    @Column(name = "tuNgay")
    private LocalDate tuNgay;

    @Column(name = "denNgay")
    private LocalDate denNgay;

    @Column(name = "tinhTrangTruocThue")
    private String tinhTrangTruocThue;

    @Column(name = "hinhAnhTruocThue")
    private String hinhAnhTruocThue;

    @Column(name = "tinhTrangSauThue")
    private String tinhTrangSauThue;

    @Column(name = "hinhAnhSauThue")
    private String hinhAnhSauThue;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa = "1";

}
