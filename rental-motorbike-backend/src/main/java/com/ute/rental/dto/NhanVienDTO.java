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
public class NhanVienDTO {

    private String maNhanVien;
    private String trangThaiXoa;
    private String maNguoiDung;
    private String maChuCuaHang;
    private List<DonHangDTO> donHangs;

}
