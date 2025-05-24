package com.ute.rental.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TINNHAN")
public class TinNhanEntity {

    @Id
    @Column(name = "maTinNhan")
    private String maTinNhan;

    @ManyToOne
    @JoinColumn(name = "maHoiThoai")
    private HoiThoaiEntity hoiThoai;

    @ManyToOne
    @JoinColumn(name = "maNguoiDung")
    private NguoiDungEntity nguoiDung;

    @Column(name = "noiDung")
    private String noiDung;

    @Column(name = "thoiGianGui")
    private LocalDateTime thoiGianGui;

}
