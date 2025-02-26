package com.ute.rental.controller.quantrivien;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.service.INguoiDungService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "nguoiDung" + ApiName.API)
@RequestMapping(ApiUrl.API_QUANTRIVIEN + "/nguoi-dung")
public class NguoiDungController {

    private final INguoiDungService nguoiDungService;

    @GetMapping("/role/{maVaiTro}")
    public ApiResponse<List<NguoiDungDTO>> getNguoiDungsByMaVaiTro(@PathVariable("maVaiTro") String maVaiTro) {
        return ApiResponse.<List<NguoiDungDTO>>builder()
                .code(200)
                .message("Danh sách người dùng")
                .result(nguoiDungService.getNguoiDungsByMaVaiTro(maVaiTro))
                .build();
    }

}
