package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;

public interface IKhieuNaiService {

    List<KhieuNaiResponseDTO> getKhieuNais();

    List<KhieuNaiResponseDTO> getKhieuNaisByMaLoaiKhieuNai(String maLoaiKhieuNai);

    List<KhieuNaiResponseDTO> getKhieuNaisByMaKhachHang(String maKhachHang);

    List<KhieuNaiResponseDTO> getKhieuNaisByMaChuCuaHang(String maChuCuaHang);

    KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO);

    KhieuNaiDTO updateKhieuNai(String maKhieuNai, KhieuNaiDTO updatedKhieuNai);

    void deleteKhieuNai(String maKhieuNai);

    KhieuNaiResponseDTO getKhieuNaiByMaKhieuNai(String maKhieuNai);

}
