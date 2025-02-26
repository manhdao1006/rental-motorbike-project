package com.ute.rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "ANHHOPDONG")
public class AnhHopDongEntity {

    @Id
    @Column(name = "maAnhHopDong")
    private String maAnhHopDong;

    @Column(name = "tenAnh")
    private String tenAnh;

    @Column(name = "duongDan")
    private String duongDan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHopDong")
    private HopDongEntity hopDong;

}
