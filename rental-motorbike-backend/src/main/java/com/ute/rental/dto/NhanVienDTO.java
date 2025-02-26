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
public class NhanVienDTO {

    private String maNhanVien;
    private String chucVu;
    private String trangThaiXoa;
    private String maNguoiDung;
    private List<XuLyKhieuNaiDTO> xuLyKhieuNais;

}
