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
@Table(name = "NHANVIEN")
public class NhanVienEntity {

    @Id
    @Column(name = "maNhanVien")
    private String maNhanVien;

    @Column(name = "chucVu")
    private String chucVu;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @MapsId
    @OneToOne
    @JoinColumn(name = "maNhanVien", referencedColumnName = "id")
    private NguoiDungEntity nguoiDung;

    @OneToMany(mappedBy = "nhanVien")
    private List<XuLyKhieuNaiEntity> xuLyKhieuNais = new ArrayList<>();

    @OneToMany(mappedBy = "nhanVien")
    private List<DonHangEntity> donHangs = new ArrayList<>();

}
