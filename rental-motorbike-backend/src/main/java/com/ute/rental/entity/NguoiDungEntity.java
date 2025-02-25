package com.ute.rental.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NGUOIDUNG")
public class NguoiDungEntity {

    @Id
    @Column(name = "maNguoiDung")
    private String maNguoiDung;

    @Column(name = "hoVaTen")
    private String hoVaTen;

    @Column(name = "email")
    private String email;

    @Column(name = "matKhau")
    private String matKhau;

    @Column(name = "soDienThoai")
    private String soDienThoai;

    @Column(name = "soCCCD")
    private String soCCCD;

    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = " trangThaiHoatDong")
    private String trangThaiHoatDong;

    @Column(name = "anhDaiDienId")
    private String anhDaiDienId;

    @Column(name = "anhDaiDien")
    private String anhDaiDien;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @Column(name = "gioiTinh")
    private String gioiTinh;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "VAITRO_NGUOIDUNG", joinColumns = @JoinColumn(name = "maNguoiDung", referencedColumnName = "maNguoiDung"), inverseJoinColumns = @JoinColumn(name = "maVaiTro", referencedColumnName = "maVaiTro"))
    private List<VaiTroEntity> vaiTros = new ArrayList<>();

    @OneToOne(mappedBy = "nguoiDung")
    private KhachHangEntity khachHang;

    @OneToOne(mappedBy = "nguoiDung")
    private NhanVienEntity nhanVien;

    @OneToOne(mappedBy = "nguoiDung")
    private ChuCuaHangEntity chuCuaHang;

}
