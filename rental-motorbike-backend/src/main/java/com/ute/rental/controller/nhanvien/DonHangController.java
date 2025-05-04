package com.ute.rental.controller.nhanvien;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.DonHangResponseDTO;
import com.ute.rental.service.IDonHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "donHangNhanVien" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/don-hang")
public class DonHangController {

    private final IDonHangService donHangService;

    @GetMapping("/maNhanVien/{maNhanVien}/{trangThaiDonHang}")
    public ApiResponse<List<DonHangResponseDTO>> getDonHangsByNhanVien(
            @PathVariable("maNhanVien") String maNhanVien,
            @PathVariable("trangThaiDonHang") String trangThaiDonHang) {
        return ApiResponse.<List<DonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách đơn hàng")
                .result(donHangService.getDonHangsByNhanVien(maNhanVien, trangThaiDonHang))
                .build();
    }

    @PutMapping("/edit/{maDonHang}")
    public ApiResponse<DonHangDTO> updateDonHang(@PathVariable("maDonHang") String maDonHang,
            @RequestBody DonHangDTO donHangDTO) {
        return ApiResponse.<DonHangDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(donHangService.updateDonHang(maDonHang, donHangDTO))
                .build();
    }

}
