package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.ChuCuaHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;

public interface IChuCuaHangService {

    List<ChuCuaHangResponseDTO> getChuCuaHangs();

    ChuCuaHangResponseDTO getChuCuaHangByMaChuCuaHang(String maNguoiDung);

    ChuCuaHangResponseDTO getChuCuaHangByMaXeMay(String maXeMay);

    ChuCuaHangResponseDTO addChuCuaHang(NguoiDungDTO nguoiDungDTO, ChuCuaHangDTO chuCuaHangDTO, MultipartFile avatar)
            throws IOException;

    ChuCuaHangResponseDTO updateChuCuaHang(String maNguoiDung, NguoiDungDTO nguoiDungDTO, ChuCuaHangDTO chuCuaHangDTO,
            MultipartFile avatar)
            throws IOException;

    void deleteChuCuaHang(String maNguoiDung);

    void banChuCuaHang(String maNguoiDung);

    ChuCuaHangResponseDTO dangKyChuCuaHang(NguoiDungDTO nguoiDungDTO, ChuCuaHangDTO chuCuaHangDTO,
            MultipartFile avatar) throws IOException;

}
