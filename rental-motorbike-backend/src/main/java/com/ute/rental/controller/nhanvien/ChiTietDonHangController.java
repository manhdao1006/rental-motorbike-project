package com.ute.rental.controller.nhanvien;

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
import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;
import com.ute.rental.service.IChiTietDonHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "chiTietDonHangNhanVien" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/chi-tiet-don-hang")
public class ChiTietDonHangController {

    private final IChiTietDonHangService chiTietDonHangService;

    @GetMapping("chiTietDonHang/{maDonHang}/{maXeMay}")
    public ApiResponse<ChiTietDonHangResponseDTO> getChiTietDonHangByMaDonHangAndMaXeMay(
            @PathVariable("maDonHang") String maDonHang,
            @PathVariable("maXeMay") String maXeMay) {
        return ApiResponse.<ChiTietDonHangResponseDTO>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng có mã đơn hàng là " + maDonHang + " và mã xe máy là " + maXeMay)
                .result(chiTietDonHangService.getChiTietDonHangByMaDonHangAndMaXeMay(maDonHang, maXeMay))
                .build();
    }

    @GetMapping("/maNhanVien/{maNhanVien}/{trangThaiDonHang}")
    public ApiResponse<List<ChiTietDonHangResponseDTO>> getChiTietDonHangsByMaNhanVien(
            @PathVariable("maNhanVien") String maNhanVien,
            @PathVariable("trangThaiDonHang") String trangThaiDonHang) {
        return ApiResponse.<List<ChiTietDonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng của chủ cửa hàng có mã là " + maNhanVien)
                .result(chiTietDonHangService.getChiTietDonHangsByMaNhanVien(maNhanVien, trangThaiDonHang))
                .build();
    }

    @PutMapping("/edit/{maDonHang}/{maXeMay}")
    public ApiResponse<ChiTietDonHangDTO> updateChiTietDonHang(
            @PathVariable("maDonHang") String maDonHang,
            @PathVariable("maXeMay") String maXeMay,
            @ModelAttribute ChiTietDonHangDTO chiTietDonHangDTO,
            @RequestParam(value = "fileHinhAnhTruocThue", required = false) MultipartFile fileHinhAnhTruocThue,
            @RequestParam(value = "fileHinhAnhSauThue", required = false) MultipartFile fileHinhAnhSauThue)
            throws IOException {
        return ApiResponse.<ChiTietDonHangDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(chiTietDonHangService.updateChiTietDonHang(maDonHang, maXeMay, chiTietDonHangDTO,
                        fileHinhAnhTruocThue, fileHinhAnhSauThue))
                .build();
    }

}
