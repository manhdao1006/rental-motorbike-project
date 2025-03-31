package com.ute.rental.controller.quantrivien;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.ChuCuaHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.service.IChuCuaHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "chuCuaHang" + ApiName.API)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/chu-cua-hang")
public class ChuCuaHangController {

    private final IChuCuaHangService chuCuaHangService;

    @GetMapping("/list")
    public ApiResponse<List<ChuCuaHangResponseDTO>> getChuCuaHangs() {
        return ApiResponse.<List<ChuCuaHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chủ cửa hàng")
                .result(chuCuaHangService.getChuCuaHangs())
                .build();
    }

    @GetMapping("/{maNguoiDung}")
    public ApiResponse<ChuCuaHangResponseDTO> getChuCuaHangByMaChuCuaHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<ChuCuaHangResponseDTO>builder()
                .code(200)
                .message("Chủ cửa hàng với mã chủ cửa hàng là " + maNguoiDung)
                .result(chuCuaHangService.getChuCuaHangByMaChuCuaHang(maNguoiDung))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<ChuCuaHangResponseDTO> addChuCuaHang(
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute ChuCuaHangDTO chuCuaHangDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<ChuCuaHangResponseDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(chuCuaHangService.addChuCuaHang(nguoiDungDTO, chuCuaHangDTO, file))
                .build();
    }

    @PutMapping("/edit/{maNguoiDung}")
    public ApiResponse<ChuCuaHangResponseDTO> updateChuCuaHang(@PathVariable("maNguoiDung") String maNguoiDung,
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute ChuCuaHangDTO chuCuaHangDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<ChuCuaHangResponseDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(chuCuaHangService.updateChuCuaHang(maNguoiDung, nguoiDungDTO, chuCuaHangDTO, file))
                .build();
    }

    @PutMapping("delete/{maNguoiDung}")
    public ApiResponse<String> deleteChuCuaHang(@PathVariable("maNguoiDung") String maNguoiDung) {
        chuCuaHangService.deleteChuCuaHang(maNguoiDung);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công!")
                .build();
    }

    @PutMapping("ban/{maNguoiDung}")
    public ApiResponse<String> banChuCuaHang(@PathVariable("maNguoiDung") String maNguoiDung) {
        chuCuaHangService.banChuCuaHang(maNguoiDung);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Cấm thành công!")
                .build();
    }

}
