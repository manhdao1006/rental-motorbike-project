package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.KhieuNaiDTO;

public interface IKhieuNaiService {

    List<KhieuNaiDTO> getKhieuNais();

    List<KhieuNaiDTO> getKhieuNaisByMaLoaiKhieuNai(String maLoaiKhieuNai);

    KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO);

    KhieuNaiDTO updateKhieuNai(String maKhieuNai, KhieuNaiDTO updatedKhieuNai);

    void deleteKhieuNai(String maKhieuNai);

    KhieuNaiDTO getKhieuNaiByMaKhieuNai(String maKhieuNai);

}
