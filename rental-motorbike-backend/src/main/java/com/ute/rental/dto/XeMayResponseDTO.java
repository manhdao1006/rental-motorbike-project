package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XeMayResponseDTO {
    private XeMayDTO xeMay;
    private DanhMucXeDTO danhMucXe;
    private ChuCuaHangDTO chuCuaHang;
    private NguoiDungDTO nguoiDung;
    private List<AnhXeMayDTO> anhXeMays;
}
