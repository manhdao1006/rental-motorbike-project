package com.ute.rental.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUANHUYEN")
public class QuanHuyenEntity {

    @Id
    @Column(name = "maQuanHuyen")
    private String maQuanHuyen;

    @Column(name = "tenQuanHuyen")
    private String tenQuanHuyen;

    @OneToMany(mappedBy = "quanHuyen")
    private List<PhuongXaEntity> phuongXas = new ArrayList<>();

}
