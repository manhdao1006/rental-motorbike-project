package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DanhMucXeDTO {

    private String maDanhMucXe;
    private String tenDanhMucXe;
    private String trangThaiXoa;
    private List<XeMayDTO> xeMays;

}
