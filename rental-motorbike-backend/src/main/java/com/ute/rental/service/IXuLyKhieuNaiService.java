package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.XuLyKhieuNaiDTO;
import com.ute.rental.dto.XuLyKhieuNaiResponseDTO;

public interface IXuLyKhieuNaiService {

    List<XuLyKhieuNaiResponseDTO> getXuLyKhieuNais();

    List<XuLyKhieuNaiResponseDTO> getXuLyKhieuNaisByMaNhanVien(String maNhanVien);

    XuLyKhieuNaiDTO addXuLyKhieuNai(XuLyKhieuNaiDTO khieuNaiDTO);

    XuLyKhieuNaiDTO updateXuLyKhieuNai(String maXuLyKhieuNai, XuLyKhieuNaiDTO updatedXuLyKhieuNai);

    void deleteXuLyKhieuNai(String maXuLyKhieuNai);

    XuLyKhieuNaiResponseDTO getXuLyKhieuNaiByMaXuLyKhieuNai(String maXuLyKhieuNai);

}
