package com.ute.rental.controller.quantrivien;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.service.ILoaiKhieuNaiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "loaiKhieuNai" + ApiName.API)
@RequestMapping(ApiUrl.API_QUANTRIVIEN + "/loai-khieu-nai")
public class LoaiKhieuNaiController {

    private final ILoaiKhieuNaiService loaiKhieuNaiService;

    @GetMapping("/list")
    public ApiResponse<List<LoaiKhieuNaiDTO>> getLoaiKhieuNais() {
        return ApiResponse.<List<LoaiKhieuNaiDTO>>builder()
                .code(200)
                .message("Danh sách loại khiếu nại")
                .result(loaiKhieuNaiService.getLoaiKhieuNais())
                .build();
    }

    @GetMapping("/maLoaiKhieuNai={maLoaiKhieuNai}")
    public ApiResponse<LoaiKhieuNaiDTO> getLoaiKhieuNaiByMaLoaiKhieuNai(
            @PathVariable("maLoaiKhieuNai") String maLoaiKhieuNai) {
        return ApiResponse.<LoaiKhieuNaiDTO>builder()
                .code(200)
                .message("Loại khiếu nại với mã loại khiếu nại là " + maLoaiKhieuNai)
                .result(loaiKhieuNaiService.getLoaiKhieuNaiByMaLoaiKhieuNai(maLoaiKhieuNai))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<LoaiKhieuNaiDTO> addLoaiKhieuNai(@RequestBody LoaiKhieuNaiDTO loaiKhieuNaiDTO) {
        return ApiResponse.<LoaiKhieuNaiDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(loaiKhieuNaiService.addLoaiKhieuNai(loaiKhieuNaiDTO))
                .build();
    }

    @PutMapping("edit/{maLoaiKhieuNai}")
    public ApiResponse<LoaiKhieuNaiDTO> updateLoaiKhieuNai(@PathVariable("maLoaiKhieuNai") String maLoaiKhieuNai,
            @RequestBody LoaiKhieuNaiDTO loaiKhieuNaiDTO) {
        return ApiResponse.<LoaiKhieuNaiDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(loaiKhieuNaiService.updateLoaiKhieuNai(maLoaiKhieuNai, loaiKhieuNaiDTO))
                .build();
    }

    @PutMapping("/delete/{maLoaiKhieuNai}")
    public ApiResponse<String> deleteLoaiKhieuNai(@PathVariable("maLoaiKhieuNai") String maLoaiKhieuNai) {
        loaiKhieuNaiService.deleteLoaiKhieuNai(maLoaiKhieuNai);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
