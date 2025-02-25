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
@Table(name = "DANHMUCXE")
public class DanhMucXeEntity {

    @Id
    @Column(name = "maDanhMucXe")
    private String maDanhMucXe;

    @Column(name = "tenDanhMucXe")
    private String tenDanhMucXe;

    @Column(name = "trangThaiXoa", nullable = false)
    private String trangThaiXoa;

    @OneToMany(mappedBy = "danhMucXe")
    private List<XeMayEntity> xeMays = new ArrayList<>();

}
