package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.DonHangResponseDTO;

public interface IDonHangService {

    List<DonHangResponseDTO> getDonHangs();

    List<DonHangResponseDTO> getDonHangsByMaChuCuaHang(String maChuCuaHang);

    List<DonHangResponseDTO> getDonHangsByMaKhachHang(String maKhachHang);

    DonHangDTO addDonHang(DonHangDTO donHangDTO);

    DonHangDTO updateDonHang(String maDonHang, DonHangDTO donHangDTO);

    void deleteDonHang(String maDonHang);

    DonHangResponseDTO getDonHangByMaDonHang(String maDonHang);

}
