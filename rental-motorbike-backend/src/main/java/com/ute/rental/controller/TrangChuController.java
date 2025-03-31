package com.ute.rental.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.XeMayResponseDTO;
import com.ute.rental.service.IXeMayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trang-chu")
public class TrangChuController {

    private final IXeMayService xeMayService;

    @GetMapping("/list-xe/{maQuanHuyen}")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysByQuanHuyen(@PathVariable("maQuanHuyen") String maQuanHuyen) {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysByQuanHuyen(maQuanHuyen))
                .build();
    }

    @GetMapping("/maXeMay={maXeMay}")
    public ApiResponse<XeMayResponseDTO> getXeMayByMaXeMay(@PathVariable("maXeMay") String maXeMay) {
        return ApiResponse.<XeMayResponseDTO>builder()
                .code(200)
                .message("Xe máy với mã xe máy " + maXeMay)
                .result(xeMayService.getXeMayByMaXeMay(maXeMay))
                .build();
    }

    @GetMapping("/list-xe-hai-chau")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysInQuanHaiChau() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysInQuanHaiChau())
                .build();
    }

    @GetMapping("/list-xe-thanh-khe")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysInQuanThanhKhe() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysInQuanThanhKhe())
                .build();
    }

    @GetMapping("/list-xe-son-tra")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysInQuanSonTra() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysInQuanSonTra())
                .build();
    }

    @GetMapping("/list-xe-ngu-hanh-son")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysInQuanNguHanhSon() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysInQuanNguHanhSon())
                .build();
    }

    @GetMapping("/list-xe-lien-chieu")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysInQuanLienChieu() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysInQuanLienChieu())
                .build();
    }

}
