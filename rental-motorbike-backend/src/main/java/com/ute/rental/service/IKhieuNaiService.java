package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;

public interface IKhieuNaiService {

    List<KhieuNaiResponseDTO> getKhieuNais();

    List<KhieuNaiResponseDTO> getKhieuNaisByMaLoaiKhieuNai(String maLoaiKhieuNai);

    List<KhieuNaiResponseDTO> getKhieuNaisByMaKhachHang(String maKhachHang);

    List<KhieuNaiResponseDTO> getKhieuNaisByMaChuCuaHang(String maChuCuaHang);

    KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO, List<MultipartFile> anhKhieuNaiList) throws IOException;

    KhieuNaiDTO updateKhieuNai(String maKhieuNai, KhieuNaiDTO updatedKhieuNai, List<MultipartFile> anhKhieuNaiList,
            List<String> deletedAnhKhieuNais) throws IOException;

    void deleteKhieuNai(String maKhieuNai);

    KhieuNaiResponseDTO getKhieuNaiByMaKhieuNai(String maKhieuNai);

}
