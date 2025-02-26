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
    private LocalDateTime ngayTraXe;
    private LocalDateTime ngayThanhToan;
    private String hoTenNguoiGiaoHang;
    private String soDienThoaiGiaoHang;
    private String trangThaiDonHang;
    private String phuongThucThanhToan;
    private String lyDoHuy;
    private String trangThaiXoa;
    private String maKhachHang;
    private String maChuCuaHang;
    private List<ChiTietDonHangDTO> chiTietDonHangs;

}
