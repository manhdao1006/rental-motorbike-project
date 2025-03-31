package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhieuNaiResponseDTO {
    private KhieuNaiDTO khieuNai;
    private LoaiKhieuNaiDTO loaiKhieuNai;
    private DonHangDTO donHang;
}
