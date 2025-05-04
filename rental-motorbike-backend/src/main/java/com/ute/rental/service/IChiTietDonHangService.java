package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;

public interface IChiTietDonHangService {

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaChuCuaHang(String maChuCuaHang, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangs();

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaDonHang(String maDonHang);

    ChiTietDonHangDTO addChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO);

    ChiTietDonHangDTO updateChiTietDonHang(String maDonHang, String maXeMay, ChiTietDonHangDTO chiTietDonHangDTO,
            MultipartFile hinhAnhTruocThue, MultipartFile hinhAnhSauThue) throws IOException;

    void deleteChiTietDonHang(String maDonHang, String maXeMay);

    ChiTietDonHangResponseDTO getChiTietDonHangByMaDonHangAndMaXeMay(String maDonHang, String maXeMay);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaKhachHang(String maKhachHang, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangKhachHang(String maNguoiDung, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaNhanVien(String maNhanVien, String trangThaiDonHang);

    List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangNhanVien(String maNguoiDung, String trangThaiDonHang);

}
