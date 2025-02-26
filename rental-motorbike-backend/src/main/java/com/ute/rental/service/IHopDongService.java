package com.ute.rental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.HopDongDTO;
import com.ute.rental.dto.HopDongResponseDTO;

public interface IHopDongService {

    List<HopDongResponseDTO> getHopDongs();

    List<HopDongResponseDTO> getHopDongsByMaChuCuaHang(String maChuCuaHang);

    List<HopDongResponseDTO> getHopDongsByMaKhachHang(String maKhachHang);

    HopDongDTO addHopDong(HopDongDTO hopDongDTO, List<MultipartFile> anhHopDongList) throws IOException;

    HopDongDTO updateHopDong(String maHopDong, HopDongDTO hopDongDTO, List<MultipartFile> anhHopDongList,
            List<String> deletedHopDongs)
            throws IOException;

    void deleteHopDong(String maHopDong);

    HopDongResponseDTO getHopDongByMaHopDong(String maHopDong);

}
