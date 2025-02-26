package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangResponseDTO {
    private DonHangDTO donHang;
    private KhachHangDTO khachHang;
    private ChuCuaHangDTO chuCuaHang;
}
