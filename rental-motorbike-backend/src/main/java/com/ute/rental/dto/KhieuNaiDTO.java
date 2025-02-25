package com.ute.rental.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhieuNaiDTO {

    private String maKhieuNai;
    private String noiDung;
    private LocalDateTime ngayKhieuNai;
    private String trangThaiXoa;
    private String maLoaiKhieuNai;
    private String maChiTietDonHang;
    private List<XuLyKhieuNaiDTO> xuLyKhieuNais;

}
