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
public class LoaiKhieuNaiDTO {

    private String maLoaiKhieuNai;
    private String tenLoaiKhieuNai;
    private String trangThaiXoa;
    private List<KhieuNaiDTO> khieuNais;

}
