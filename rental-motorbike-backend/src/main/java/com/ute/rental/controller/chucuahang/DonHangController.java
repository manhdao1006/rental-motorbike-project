package com.ute.rental.controller.chucuahang;

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
@RestController(value = "donHang" + ApiName.API)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/don-hang")
public class DonHangController {

    private final IDonHangService donHangService;

    @GetMapping("/maChuCuaHang/{maChuCuaHang}/{trangThaiDonHang}")
    public ApiResponse<List<DonHangResponseDTO>> getDonHangsByChuCuaHang(
            @PathVariable("maChuCuaHang") String maChuCuaHang,
            @PathVariable("trangThaiDonHang") String trangThaiDonHang) {
        return ApiResponse.<List<DonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách đơn hàng có mã chủ cửa hàng là " + maChuCuaHang)
                .result(donHangService.getDonHangsByChuCuaHang(maChuCuaHang, trangThaiDonHang))
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
