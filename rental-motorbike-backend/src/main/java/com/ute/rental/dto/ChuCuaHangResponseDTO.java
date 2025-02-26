package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuCuaHangResponseDTO {
    private NguoiDungDTO nguoiDung;
    private ChuCuaHangDTO chuCuaHang;
}
