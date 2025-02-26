package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HopDongResponseDTO {
    private HopDongDTO hopDong;
    private KhachHangDTO khachHang;
    private ChuCuaHangDTO chuCuaHang;
    private List<AnhHopDongDTO> anhHopDongs;
}
