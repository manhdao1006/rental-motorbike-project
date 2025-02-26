package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.dto.KhachHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;

public interface IKhachHangService {

    List<KhachHangResponseDTO> getKhachHangs();

    KhachHangResponseDTO getKhachHangByMaKhachHang(String maNguoiDung);

    KhachHangResponseDTO addKhachHang(NguoiDungDTO nguoiDungDTO, KhachHangDTO khachHangDTO, MultipartFile avatar)
            throws IOException;

    KhachHangResponseDTO updateKhachHang(String maNguoiDung, NguoiDungDTO nguoiDungDTO, KhachHangDTO khachHangDTO,
            MultipartFile avatar)
            throws IOException;

    void deleteKhachHang(String maNguoiDung);

    void banKhachHang(String maNguoiDung);

}
