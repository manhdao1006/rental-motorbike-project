package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XeMayResponseDTO {
    private XeMayDTO sanPham;
    private DanhMucXeDTO nguoiBan;
    private ChuCuaHangDTO danhMucCon;
    private List<AnhXeMayDTO> anhXeMays;
    private List<AnhCaVetXeDTO> anhCaVets;
}
