package com.ute.rental.controller.khachhang;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.XeMayResponseDTO;
import com.ute.rental.service.IXeMayService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "xeMay" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/xe-may")
public class XeMayController {

    private final IXeMayService xeMayService;

    @GetMapping("/list")
    public ApiResponse<List<XeMayResponseDTO>> getXeMays() {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMays())
                .build();
    }
}
