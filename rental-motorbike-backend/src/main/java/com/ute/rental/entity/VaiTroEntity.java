package com.ute.rental.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VAITRO")
public class VaiTroEntity {

    @Id
    @Column(name = "maVaiTro")
    private String maVaiTro;

    @Column(name = "tenVaiTro")
    private String tenVaiTro;

    @ManyToMany(mappedBy = "vaiTros")
    private List<NguoiDungEntity> nguoiDungs = new ArrayList<>();

}
