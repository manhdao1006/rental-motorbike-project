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
import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;
import com.ute.rental.service.IChiTietDonHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "chiTietDonHang" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/chi-tiet-don-hang")
public class ChiTietDonHangController {

    private final IChiTietDonHangService chiTietDonHangService;

    @GetMapping("/list/{maNguoiDung}")
    public ApiResponse<List<ChiTietDonHangResponseDTO>> getChiTietDonHangs() {
        return ApiResponse.<List<ChiTietDonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng")
                .result(chiTietDonHangService.getChiTietDonHangs())
                .build();
    }

    @GetMapping("/maChiTietDonHang={maChiTietDonHang}")
    public ApiResponse<ChiTietDonHangResponseDTO> getChiTietDonHangByMaChiTietDonHang(
            @PathVariable("maChiTietDonHang") String maChiTietDonHang) {
        return ApiResponse.<ChiTietDonHangResponseDTO>builder()
                .code(200)
                .message("Danh sách chi tiết đơn hàng có mã là " + maChiTietDonHang)
                .result(chiTietDonHangService.getChiTietDonHangByMaChiTietDonHang(maChiTietDonHang))
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

    @PutMapping("/edit/{maChiTietDonHang}")
    public ApiResponse<ChiTietDonHangDTO> updateChiTietDonHang(
            @PathVariable String maChiTietDonHang,
            @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ApiResponse.<ChiTietDonHangDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(chiTietDonHangService.updateChiTietDonHang(maChiTietDonHang, chiTietDonHangDTO))
                .build();
    }

}
