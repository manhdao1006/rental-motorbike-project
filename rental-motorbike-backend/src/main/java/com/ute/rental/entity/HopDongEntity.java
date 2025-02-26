package com.ute.rental.entity;

import java.time.LocalDateTime;
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
@Table(name = "HOPDONG")
public class HopDongEntity {

    @Id
    @Column(name = "maHopDong")
    private String maHopDong;

    @Column(name = "tenHopDong")
    private String tenHopDong;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKhachHang")
    private KhachHangEntity khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChuCuaHang")
    private ChuCuaHangEntity chuCuaHang;

    @OneToMany(mappedBy = "hopDong", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<AnhHopDongEntity> anhHopDongs = new ArrayList<>();

}
