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
public class DonHangDTO {

    private String maDonHang;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayGiaoXe;
    private String diaChiGiaoXe;
    private String trangThaiDonHang;
    private LocalDateTime ngayThanhToan;
    private String phuongThucThanhToan;
    private LocalDateTime ngayTraXe;
    private String lyDoHuy;
    private String tepDinhKem;
    private String trangThaiXoa;
    private String maKhachHang;
    private String maNhanVien;
    private List<ChiTietDonHangDTO> chiTietDonHangs;
    private List<KhieuNaiDTO> khieuNais;

}
