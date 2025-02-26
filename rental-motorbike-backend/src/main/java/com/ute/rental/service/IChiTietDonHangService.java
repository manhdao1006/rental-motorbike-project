package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;

public interface IChiTietDonHangService {

    List<ChiTietDonHangResponseDTO> getChiTietDonHangs();

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaDonHang(String maDonHang);

    ChiTietDonHangDTO addChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO);

    ChiTietDonHangDTO updateChiTietDonHang(String maChiTietDonHang, ChiTietDonHangDTO chiTietDonHangDTO);

    void deleteChiTietDonHang(String maChiTietDonHang);

    ChiTietDonHangResponseDTO getChiTietDonHangByMaChiTietDonHang(String maChiTietDonHang);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaKhachHang(String maNguoiDung);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangKhachHang(String maNguoiDung, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaChuCuaHang(String maNguoiDung);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangChuCuaHang(String maNguoiDung, String trangThaiDonHang);

}
