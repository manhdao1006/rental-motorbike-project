package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NhanVienDTO;
import com.ute.rental.dto.NhanVienResponseDTO;

public interface INhanVienService {

    List<NhanVienResponseDTO> getNhanViens();

    NhanVienResponseDTO getNhanVienByMaNhanVien(String maNguoiDung);

    NhanVienResponseDTO addNhanVien(NguoiDungDTO nguoiDungDTO, NhanVienDTO nhanVienDTO, MultipartFile avatar)
            throws IOException;

    NhanVienResponseDTO updateNhanVien(String maNguoiDung, NguoiDungDTO nguoiDungDTO, NhanVienDTO nhanVienDTO,
            MultipartFile avatar)
            throws IOException;

    void deleteNhanVien(String maNguoiDung);

    void banNhanVien(String maNguoiDung);

    List<NhanVienResponseDTO> getNhanViensByChuCuaHang(String maChuCuaHang);
}
