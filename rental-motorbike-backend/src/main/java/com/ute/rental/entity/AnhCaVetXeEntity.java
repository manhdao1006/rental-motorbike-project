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
@Table(name = "ANHCAVETXE")
public class AnhCaVetXeEntity {

    @Id
    @Column(name = "maAnhCaVet")
    private String maAnhCaVet;

    @Column(name = "tenAnhId")
    private String tenAnhId;

    @Column(name = "tenAnh")
    private String tenAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maXeMay")
    private XeMayEntity xeMay;

}
