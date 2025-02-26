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
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.dto.KhachHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.service.IKhachHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "khachHang" + ApiName.API)
@RequestMapping(ApiUrl.API_QUANTRIVIEN + "/khach-hang")
public class KhachHangController {

    private final IKhachHangService khachHangService;

    @GetMapping("/list")
    public ApiResponse<List<KhachHangResponseDTO>> getKhachHangs() {
        return ApiResponse.<List<KhachHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách người mua")
                .result(khachHangService.getKhachHangs())
                .build();
    }

    @GetMapping("/{maNguoiDung}")
    public ApiResponse<KhachHangResponseDTO> getKhachHangByMaKhachHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<KhachHangResponseDTO>builder()
                .code(200)
                .message("Người mua với mã người mua là " + maNguoiDung)
                .result(khachHangService.getKhachHangByMaKhachHang(maNguoiDung))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<KhachHangResponseDTO> addKhachHang(
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute KhachHangDTO khachHangDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<KhachHangResponseDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(khachHangService.addKhachHang(nguoiDungDTO, khachHangDTO, file))
                .build();
    }

    @PutMapping("/edit/{maNguoiDung}")
    public ApiResponse<KhachHangResponseDTO> updateKhachHang(@PathVariable("maNguoiDung") String maNguoiDung,
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute KhachHangDTO khachHangDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<KhachHangResponseDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(khachHangService.updateKhachHang(maNguoiDung, nguoiDungDTO, khachHangDTO, file))
                .build();
    }

    @PutMapping("delete/{maNguoiDung}")
    public ApiResponse<String> deleteKhachHang(@PathVariable("maNguoiDung") String maNguoiDung) {
        khachHangService.deleteKhachHang(maNguoiDung);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công!")
                .build();
    }

    @PutMapping("ban/{maNguoiDung}")
    public ApiResponse<String> banKhachHang(@PathVariable("maNguoiDung") String maNguoiDung) {
        khachHangService.banKhachHang(maNguoiDung);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Cấm thành công!")
                .build();
    }

}
