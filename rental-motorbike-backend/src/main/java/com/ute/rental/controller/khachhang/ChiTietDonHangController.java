package com.ute.rental.controller.khachhang;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RestController(value = "chiTietDonHang" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/chi-tiet-don-hang")
public class ChiTietDonHangController {

    private final IChiTietDonHangService chiTietDonHangService;

    @GetMapping("/maKhachHang/{maKhachHang}/{trangThaiDonHang}")
    public ApiResponse<List<ChiTietDonHangResponseDTO>> getChiTietDonHangsByMaKhachHang(
            @PathVariable("maKhachHang") String maKhachHang,
            @PathVariable("trangThaiDonHang") String trangThaiDonHang) {
        return ApiResponse.<List<ChiTietDonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng của khách hàng có mã là " + maKhachHang)
                .result(chiTietDonHangService.getChiTietDonHangsByMaKhachHang(maKhachHang, trangThaiDonHang))
                .build();
    }

    @GetMapping("/maChiTietDonHang/{maChiTietDonHang}")
    public ApiResponse<ChiTietDonHangResponseDTO> getChiTietDonHangByMaDonHangAndMaXeMay(
            @PathVariable("maDonHang") String maDonHang, @PathVariable("maXeMay") String maXeMay) {
        return ApiResponse.<ChiTietDonHangResponseDTO>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng có mã đơn hàng là " + maDonHang + " và mã xe máy là " + maXeMay)
                .result(chiTietDonHangService.getChiTietDonHangByMaDonHangAndMaXeMay(maDonHang, maXeMay))
                .build();
    }

    @GetMapping("/list/{maNguoiDung}/{trangThaiDonHang}")
    public ApiResponse<List<ChiTietDonHangResponseDTO>> getByTrangThaiDonHangKhachHang(
            @PathVariable("maNguoiDung") String maNguoiDung,
            @PathVariable("trangThaiDonHang") String trangThaiDonHang) {
        return ApiResponse.<List<ChiTietDonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng của khách hàng có mã là " + maNguoiDung)
                .result(chiTietDonHangService.getByTrangThaiDonHangKhachHang(maNguoiDung, trangThaiDonHang))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<ChiTietDonHangDTO> addChiTietDonHang(@RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ApiResponse.<ChiTietDonHangDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(chiTietDonHangService.addChiTietDonHang(chiTietDonHangDTO))
                .build();
    }

    @PutMapping("/edit/{maDonHang}/{maXeMay}")
    public ApiResponse<ChiTietDonHangDTO> updateChiTietDonHang(
            @PathVariable("maDonHang") String maDonHang,
            @PathVariable("maXeMay") String maXeMay,
            @ModelAttribute ChiTietDonHangDTO chiTietDonHangDTO,
            @RequestParam(value = "hinhAnhTruocThue", required = false) MultipartFile hinhAnhTruocThue,
            @RequestParam(value = "hinhAnhSauThue", required = false) MultipartFile hinhAnhSauThue) throws IOException {
        return ApiResponse.<ChiTietDonHangDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(chiTietDonHangService.updateChiTietDonHang(maDonHang, maXeMay, chiTietDonHangDTO,
                        hinhAnhTruocThue, hinhAnhSauThue))
                .build();
    }

}
