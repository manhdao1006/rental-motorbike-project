package com.ute.rental.entity;

import java.math.BigDecimal;
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
@Table(name = "CHITIETDONHANG")
public class ChiTietDonHangEntity {

    @Id
    @Column(name = "maChiTietDonHang")
    private String maChiTietDonHang;

    @Column(name = "soLuongThue")
    private BigDecimal soLuongThue;

    @Column(name = "giaThue")
    private BigDecimal giaThue;

    @Column(name = "tuNgay")
    private LocalDateTime tuNgay;

    @Column(name = "denNgay")
    private LocalDateTime denNgay;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonHang")
    private DonHangEntity donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maXeMay")
    private XeMayEntity xeMay;

    @OneToMany(mappedBy = "chiTietDonHang")
    private List<KhieuNaiEntity> khieuNais = new ArrayList<>();

}
