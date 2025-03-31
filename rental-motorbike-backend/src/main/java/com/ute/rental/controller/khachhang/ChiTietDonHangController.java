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
import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;
import com.ute.rental.service.IChiTietDonHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "chiTietDonHangKhachHang" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/chi-tiet-don-hang")
public class ChiTietDonHangController {

    private final IChiTietDonHangService chiTietDonHangService;

    @GetMapping("/list/{maNguoiDung}")
    public ApiResponse<List<ChiTietDonHangResponseDTO>> getChiTietDonHangsByMaKhachHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<List<ChiTietDonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng của khách hàng có mã là " + maNguoiDung)
                .result(chiTietDonHangService.getChiTietDonHangsByMaKhachHang(maNguoiDung))
                .build();
    }

    @GetMapping("/maChiTietDonHang={maChiTietDonHang}")
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
    public ApiResponse<ChiTietDonHangDTO> updateChiTietDonHang(@PathVariable("maDonHang") String maDonHang,
            @PathVariable("maXeMay") String maXeMay, @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ApiResponse.<ChiTietDonHangDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(chiTietDonHangService.updateChiTietDonHang(maDonHang, maXeMay, chiTietDonHangDTO))
                .build();
    }

}
