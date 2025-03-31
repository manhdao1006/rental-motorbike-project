package com.ute.rental.controller.chucuahang;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.DonHangResponseDTO;
import com.ute.rental.service.IDonHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "donHang" + ApiName.API)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/don-hang")
public class DonHangController {

    private final IDonHangService donHangService;

    @GetMapping("/list/{maNguoiDung}")
    public ApiResponse<List<DonHangResponseDTO>> getDonHangsByMaNhanVien(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<List<DonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách đơn hàng")
                .result(donHangService.getDonHangsByMaNhanVien(maNguoiDung))
                .build();
    }

}
