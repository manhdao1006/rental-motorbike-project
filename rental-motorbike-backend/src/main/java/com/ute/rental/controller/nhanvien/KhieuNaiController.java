package com.ute.rental.controller.nhanvien;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.KhieuNaiResponseDTO;
import com.ute.rental.service.IKhieuNaiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "khieuNai" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/khieu-nai")
public class KhieuNaiController {

    private final IKhieuNaiService khieuNaiService;

    @GetMapping("/list")
    public ApiResponse<List<KhieuNaiResponseDTO>> getKhieuNais() {
        return ApiResponse.<List<KhieuNaiResponseDTO>>builder()
                .code(200)
                .message("Danh sách khiếu nại")
                .result(khieuNaiService.getKhieuNais())
                .build();
    }

}
