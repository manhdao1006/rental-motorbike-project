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
public class HopDongDTO {

    private String maHopDong;
    private String tenHopDong;
    private LocalDateTime ngayTao;
    private String trangThaiXoa;
    private String maKhachHang;
    private String maChuCuaHang;
    private List<AnhHopDongDTO> anhHopDongs;
}
