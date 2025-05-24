package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TinNhanDTO {

    private String maHoiThoai;
    private String maNguoiDung;
    private String noiDung;
    private String thoiGianGui;

}
