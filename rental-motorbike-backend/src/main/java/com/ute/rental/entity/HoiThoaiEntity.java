package com.ute.rental.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "HOITHOAI")
public class HoiThoaiEntity {

    @Id
    @Column(name = "maHoiThoai")
    private String maHoiThoai;

    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private NguoiDungEntity khachHang;

    @ManyToOne
    @JoinColumn(name = "maChuCuaHang")
    private NguoiDungEntity chuCuaHang;

    @OneToMany(mappedBy = "hoiThoai")
    private List<TinNhanEntity> tinNhans;

}
