package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangResponseDTO {
    private DonHangDTO donHang;
    private KhachHangDTO khachHang;
    private NhanVienDTO nhanVien;
    private ChuCuaHangDTO chuCuaHang;
    private List<ChiTietDonHangDTO> chiTietDonHangs;
}
