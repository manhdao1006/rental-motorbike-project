package com.ute.rental.controller.nhanvien;

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
import com.ute.rental.dto.XuLyKhieuNaiDTO;
import com.ute.rental.dto.XuLyKhieuNaiResponseDTO;
import com.ute.rental.service.IXuLyKhieuNaiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "xuLyXuLyKhieuNai" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/xu-ly-khieu-nai")
public class XuLyKhieuNaiController {

    private final IXuLyKhieuNaiService xuLyKhieuNaiService;

    @GetMapping("/list")
    public ApiResponse<List<XuLyKhieuNaiResponseDTO>> getXuLyKhieuNais() {
        return ApiResponse.<List<XuLyKhieuNaiResponseDTO>>builder()
                .code(200)
                .message("Danh sách xử lý khiếu nại")
                .result(xuLyKhieuNaiService.getXuLyKhieuNais())
                .build();
    }

    @GetMapping("/maXuLyKhieuNai={maXuLyKhieuNai}")
    public ApiResponse<XuLyKhieuNaiResponseDTO> getXuLyKhieuNaiByMaXuLyKhieuNai(
            @PathVariable("maXuLyKhieuNai") String maXuLyKhieuNai) {
        return ApiResponse.<XuLyKhieuNaiResponseDTO>builder()
                .code(200)
                .message("Xử lý khiếu nại với mã xử lý khiếu nại là " + maXuLyKhieuNai)
                .result(xuLyKhieuNaiService.getXuLyKhieuNaiByMaXuLyKhieuNai(maXuLyKhieuNai))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<XuLyKhieuNaiDTO> addXuLyKhieuNai(@RequestBody XuLyKhieuNaiDTO xuLyKhieuNaiDTO) {
        return ApiResponse.<XuLyKhieuNaiDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(xuLyKhieuNaiService.addXuLyKhieuNai(xuLyKhieuNaiDTO))
                .build();
    }

    @PutMapping("edit/{maXuLyKhieuNai}")
    public ApiResponse<XuLyKhieuNaiDTO> updateXuLyKhieuNai(@PathVariable("maXuLyKhieuNai") String maXuLyKhieuNai,
            @RequestBody XuLyKhieuNaiDTO xuLyKhieuNaiDTO) {
        return ApiResponse.<XuLyKhieuNaiDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(xuLyKhieuNaiService.updateXuLyKhieuNai(maXuLyKhieuNai, xuLyKhieuNaiDTO))
                .build();
    }

    @PutMapping("/delete/{maXuLyKhieuNai}")
    public ApiResponse<String> deleteXuLyKhieuNai(@PathVariable("maXuLyKhieuNai") String maXuLyKhieuNai) {
        xuLyKhieuNaiService.deleteXuLyKhieuNai(maXuLyKhieuNai);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
