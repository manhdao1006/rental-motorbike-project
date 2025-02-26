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
public class ChuCuaHangDTO {

    private String maChuCuaHang;
    private String tenCuaHang;
    private String diaChiCuaHang;
    private String soDienThoaiCuaHang;
    private LocalDateTime ngayDangKy;
    private String trangThaiXoa;
    private String maNguoiDung;
    private String maPhuongXa;
    private List<XeMayDTO> xeMays;
    private List<HopDongDTO> hopDongs;

}
