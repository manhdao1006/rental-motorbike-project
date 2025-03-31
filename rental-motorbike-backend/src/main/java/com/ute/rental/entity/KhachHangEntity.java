package com.ute.rental.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KHACHHANG")
public class KhachHangEntity {

    @Id
    @Column(name = "maKhachHang")
    private String maKhachHang;

    @Column(name = "soGPLX")
    private String soGPLX;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa = "1";

    @MapsId
    @OneToOne
    @JoinColumn(name = "maKhachHang", referencedColumnName = "maNguoiDung")
    private NguoiDungEntity nguoiDung;

    @OneToMany(mappedBy = "khachHang")
    private List<DonHangEntity> donHangs = new ArrayList<>();

}
