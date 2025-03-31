package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;

public interface IChiTietDonHangService {

    List<ChiTietDonHangResponseDTO> getChiTietDonHangs();

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaDonHang(String maDonHang);

    ChiTietDonHangDTO addChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO);

    ChiTietDonHangDTO updateChiTietDonHang(String maDonHang, String maXeMay, ChiTietDonHangDTO chiTietDonHangDTO);

    void deleteChiTietDonHang(String maDonHang, String maXeMay);

    ChiTietDonHangResponseDTO getChiTietDonHangByMaDonHangAndMaXeMay(String maDonHang, String maXeMay);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaKhachHang(String maNguoiDung);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangKhachHang(String maNguoiDung, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaNhanVien(String maNguoiDung);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangNhanVien(String maNguoiDung, String trangThaiDonHang);

}
