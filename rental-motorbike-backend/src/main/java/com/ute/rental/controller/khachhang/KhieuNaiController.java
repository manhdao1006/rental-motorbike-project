package com.ute.rental.controller.khachhang;

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
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;
import com.ute.rental.service.IKhieuNaiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "khieuNai" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/khieu-nai")
public class KhieuNaiController {

    private final IKhieuNaiService khieuNaiService;

    @GetMapping("/list/{maNguoiDung}")
    public ApiResponse<List<KhieuNaiResponseDTO>> getKhieuNaisByMaKhachHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<List<KhieuNaiResponseDTO>>builder()
                .code(200)
                .message("Danh sách khiếu nại")
                .result(khieuNaiService.getKhieuNaisByMaKhachHang(maNguoiDung))
                .build();
    }

    @GetMapping("/maKhieuNai/{maKhieuNai}")
    public ApiResponse<KhieuNaiResponseDTO> getKhieuNaiByMaKhieuNai(@PathVariable("maKhieuNai") String maKhieuNai) {
        return ApiResponse.<KhieuNaiResponseDTO>builder()
                .code(200)
                .message("Khiếu nại với mã khiếu nại là " + maKhieuNai)
                .result(khieuNaiService.getKhieuNaiByMaKhieuNai(maKhieuNai))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<KhieuNaiDTO> addKhieuNai(@RequestBody KhieuNaiDTO khieuNaiDTO) {
        return ApiResponse.<KhieuNaiDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(khieuNaiService.addKhieuNai(khieuNaiDTO))
                .build();
    }

    @PutMapping("edit/{maKhieuNai}")
    public ApiResponse<KhieuNaiDTO> updateKhieuNai(@PathVariable("maKhieuNai") String maKhieuNai,
            @RequestBody KhieuNaiDTO khieuNaiDTO) {
        return ApiResponse.<KhieuNaiDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(khieuNaiService.updateKhieuNai(maKhieuNai, khieuNaiDTO))
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
