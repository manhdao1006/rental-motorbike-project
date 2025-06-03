package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.DonHangResponseDTO;

public interface IDonHangService {

    List<DonHangResponseDTO> getDonHangs();

    List<DonHangResponseDTO> getDonHangsByChuCuaHang(String maChuCuaHang, String trangThaiDonHang);

    List<DonHangResponseDTO> getDonHangsByNhanVien(String maNhanVien, String trangThaiDonHang);

    List<DonHangResponseDTO> getDonHangsByKhachHang(String maKhachHang);

    DonHangDTO addDonHang(DonHangDTO donHangDTO);

    DonHangDTO updateDonHang(String maDonHang, DonHangDTO donHangDTO);

    void deleteDonHang(String maDonHang);

    DonHangResponseDTO getDonHangByMaDonHang(String maDonHang);

    void capNhatTrangThaiVaLyDoHuy(String maDonHang, String trangThai, String lyDoHuy);

}
