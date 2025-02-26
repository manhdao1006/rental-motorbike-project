package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangResponseDTO {
    private ChiTietDonHangDTO chiTietDonHang;
    private DonHangDTO donHang;
    private XeMayDTO xeMay;
}
