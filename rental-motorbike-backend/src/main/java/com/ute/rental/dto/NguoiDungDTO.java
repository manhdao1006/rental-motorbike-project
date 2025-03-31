package com.ute.rental.dto;

import java.time.LocalDate;
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
public class NguoiDungDTO {

    private String maNguoiDung;
    private String hoVaTen;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String soCCCD;
    private String diaChi;
    private String trangThaiHoatDong;
    private String anhDaiDienId;
    private String anhDaiDien;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private LocalDateTime ngayDangKy;
    private String trangThaiXoa;
    private List<VaiTroDTO> vaiTros;
    private String maKhachHang;
    private String maChuCuaHang;
    private String maNhanVien;

}
