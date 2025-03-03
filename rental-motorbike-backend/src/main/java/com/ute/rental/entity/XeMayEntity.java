package com.ute.rental.entity;

import java.math.BigDecimal;
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
@Table(name = "XEMAY")
public class XeMayEntity {

    @Id
    @Column(name = "maXeMay")
    private String maXeMay;

    @Column(name = "bienSoXeMay")
    private String bienSoXeMay;

    @Column(name = "tenXeMay")
    private String tenXeMay;

    @Column(name = "mauXeMay")
    private String mauXeMay;

    @Column(name = "giaThue")
    private BigDecimal giaThue;

    @Column(name = "soKhung")
    private String soKhung;

    @Column(name = "soMay")
    private String soMay;

    @Column(name = "loaiXeMay")
    private String loaiXeMay;

    @Column(name = "lyDoTuChoi")
    private String lyDoTuChoi;

    @Column(name = " trangThaiHoatDong")
    private String trangThaiHoatDong;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa = "1";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDanhMucXe")
    private DanhMucXeEntity danhMucXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChuCuaHang")
    private ChuCuaHangEntity chuCuaHang;

    @OneToMany(mappedBy = "xeMay", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<AnhXeMayEntity> anhXeMays = new ArrayList<>();

    @OneToMany(mappedBy = "xeMay", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<AnhCaVetXeEntity> anhCaVetXes = new ArrayList<>();

    @OneToMany(mappedBy = "xeMay")
    private List<ChiTietDonHangEntity> chiTietDonHangs = new ArrayList<>();

}
