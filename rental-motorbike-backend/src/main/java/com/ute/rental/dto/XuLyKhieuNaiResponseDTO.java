package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XuLyKhieuNaiResponseDTO {
    private XuLyKhieuNaiDTO xuLyKhieuNai;
    private NhanVienDTO nhanVien;
    private KhieuNaiDTO khieuNai;
}
