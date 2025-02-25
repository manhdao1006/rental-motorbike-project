package com.ute.rental.dto;

import java.math.BigDecimal;
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
public class ChiTietDonHangDTO {

    private String maChiTietDonHang;
    private BigDecimal soLuongThue;
    private BigDecimal giaThue;
    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;
    private String trangThaiXoa;
    private String maDonHang;
    private String maXeMay;
    private List<KhieuNaiDTO> khieuNais;

}
