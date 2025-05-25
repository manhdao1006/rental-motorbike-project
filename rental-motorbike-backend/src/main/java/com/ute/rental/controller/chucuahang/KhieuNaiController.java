package com.ute.rental.controller.chucuahang;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;
import com.ute.rental.service.IKhieuNaiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "khieuNai" + ApiName.API)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/khieu-nai")
public class KhieuNaiController {

    private final IKhieuNaiService khieuNaiService;

    @GetMapping("/list/{maNguoiDung}")
    public ApiResponse<List<KhieuNaiResponseDTO>> getKhieuNaisByMaChuCuaHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<List<KhieuNaiResponseDTO>>builder()
                .code(200)
                .message("Danh sách khiếu nại")
                .result(khieuNaiService.getKhieuNaisByMaChuCuaHang(maNguoiDung))
                .build();
    }

    @PutMapping("edit/{maKhieuNai}")
    public ApiResponse<KhieuNaiDTO> updateKhieuNai(@PathVariable("maKhieuNai") String maKhieuNai,
            @ModelAttribute KhieuNaiDTO khieuNaiDTO,
            @RequestParam(value = "anhKhieuNaiList", required = false) List<MultipartFile> anhKhieuNaiList,
            @RequestParam(value = "deletedAnhKhieuNais", required = false) List<String> deletedAnhKhieuNais)
            throws IOException {

        return ApiResponse.<KhieuNaiDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(khieuNaiService.updateKhieuNai(maKhieuNai, khieuNaiDTO, anhKhieuNaiList, deletedAnhKhieuNais))
                .build();
    }

    @PutMapping("/delete/{maKhieuNai}")
    public ApiResponse<String> deleteKhieuNai(@PathVariable("maKhieuNai") String maKhieuNai) {
        khieuNaiService.deleteKhieuNai(maKhieuNai);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
