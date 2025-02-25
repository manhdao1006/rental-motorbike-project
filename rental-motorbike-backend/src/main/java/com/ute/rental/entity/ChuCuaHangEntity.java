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
@Table(name = "CHUCUAHANG")
public class ChuCuaHangEntity {

    @Id
    @Column(name = "maChuCuaHang")
    private String maChuCuaHang;

    @Column(name = "tenCuaHang")
    private String tenCuaHang;

    @Column(name = "ngayDangKy")
    private LocalDateTime ngayDangKy;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @MapsId
    @OneToOne
    @JoinColumn(name = "maChuCuaHang", referencedColumnName = "maNguoiDung")
    private NguoiDungEntity nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhuongXa")
    private PhuongXaEntity phuongXa;

    @OneToMany(mappedBy = "chuCuaHang")
    private List<XeMayEntity> xeMays = new ArrayList<>();

}
