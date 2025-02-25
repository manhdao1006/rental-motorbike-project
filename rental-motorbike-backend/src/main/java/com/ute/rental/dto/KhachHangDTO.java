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
public class KhachHangDTO {

    private String maKhachHang;
    private String soGPLX;
    private LocalDateTime ngayDangKy;
    private String trangThaiXoa;
    private String maNguoiDung;
    private List<DonHangDTO> donHangs;

}
