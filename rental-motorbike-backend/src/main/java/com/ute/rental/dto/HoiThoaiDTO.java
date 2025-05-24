package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoiThoaiDTO {

    private String maHoiThoai;
    private String maKhachHang;
    private String maChuCuaHang;
    private List<TinNhanDTO> tinNhans;

}
