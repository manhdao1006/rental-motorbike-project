package com.ute.rental.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangDTO {

    private String maDonHang;
    private String maXeMay;
    private BigDecimal giaThue;
    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;
    private String trangThaiXoa;
    private String tinhTrangTruocThue;
    private String hinhAnhTruocThue;
    private String tinhTrangSauThue;
    private String hinhAnhSauThue;

}
