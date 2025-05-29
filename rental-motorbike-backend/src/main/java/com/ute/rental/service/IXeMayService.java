package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.XeMayDTO;
import com.ute.rental.dto.XeMayResponseDTO;

public interface IXeMayService {

    List<XeMayResponseDTO> getXeMays();

    List<XeMayResponseDTO> getXeMaysByMaChuCuaHang(String maChuCuaHang);

    XeMayDTO addXeMay(XeMayDTO xeMayDTO, List<MultipartFile> anhXeMayList) throws IOException;

    XeMayDTO updateXeMay(String maXeMay, XeMayDTO xeMayDTO, List<MultipartFile> anhXeMayList,
            List<String> deletedAnhXeMays) throws IOException;

    void deleteXeMay(String maXeMay);

    void capNhatTrangThaiXeMay(String maXeMay, String trangThaiHoatDong);

    XeMayResponseDTO getXeMayByMaXeMay(String maXeMay);

    List<XeMayResponseDTO> getXeMaysInQuanHaiChau();

    List<XeMayResponseDTO> getXeMaysInQuanThanhKhe();

    List<XeMayResponseDTO> getXeMaysInQuanSonTra();

    List<XeMayResponseDTO> getXeMaysInQuanNguHanhSon();

    List<XeMayResponseDTO> getXeMaysInQuanLienChieu();

    List<XeMayResponseDTO> getXeMaysByQuanHuyen(String maQuanHuyen);

}
