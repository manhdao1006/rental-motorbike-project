package com.ute.rental.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XeMayDTO {

    private String maXeMay;
    private String bienSoXeMay;
    private String tenXeMay;
    private String mauXeMay;
    private BigDecimal giaThue;
    private String soKhung;
    private String soMay;
    private String loaiXeMay;
    private String lyDoTuChoi;
    private String trangThaiHoatDong;
    private String trangThaiXoa;
    private String maDanhMucXe;
    private String maChuCuaHang;
    private List<ChiTietDonHangDTO> chiTietDonHangs;
    private List<AnhXeMayDTO> anhXeMays;
    private List<AnhCaVetXeDTO> anhCaVetXes;

}
