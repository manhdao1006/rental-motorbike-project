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
    private String noiDungKhieuNai;
    private LocalDateTime ngayKhieuNai;
    private String noiDungPhanHoi;
    private LocalDateTime ngayPhanHoi;
    private String trangThaiXuLy;
    private String trangThaiXoa;
    private String maLoaiKhieuNai;
    private String maDonHang;
    private List<AnhKhieuNaiDTO> anhKhieuNais;

}
