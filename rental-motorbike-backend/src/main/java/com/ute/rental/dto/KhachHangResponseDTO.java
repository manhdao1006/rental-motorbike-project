package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangResponseDTO {
    private NguoiDungDTO nguoiDung;
    private KhachHangDTO khachHang;
}
