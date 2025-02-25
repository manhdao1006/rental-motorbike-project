package com.ute.rental.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XuLyKhieuNaiDTO {

    private String maXuLyKhieuNai;
    private String noiDung;
    private LocalDateTime ngayXuLy;
    private String trangThaiXoa;
    private String maKhieuNai;
    private String maNhanVien;

}
