package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.LoaiKhieuNaiDTO;

public interface ILoaiKhieuNaiService {

    List<LoaiKhieuNaiDTO> getLoaiKhieuNais();

    LoaiKhieuNaiDTO getLoaiKhieuNaiByMaLoaiKhieuNai(String maLoaiKhieuNai);

    LoaiKhieuNaiDTO addLoaiKhieuNai(LoaiKhieuNaiDTO danhMucDTO);

    LoaiKhieuNaiDTO updateLoaiKhieuNai(String maLoaiKhieuNai, LoaiKhieuNaiDTO updatedLoaiKhieuNai);

    void deleteLoaiKhieuNai(String maLoaiKhieuNai);

}
