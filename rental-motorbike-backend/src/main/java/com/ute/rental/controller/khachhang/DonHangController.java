package com.ute.rental.controller.khachhang;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RestController(value = "donHang" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/don-hang")
public class DonHangController {

    private final IDonHangService donHangService;

    @GetMapping("/maKhachHang/{maKhachHang}")
    public ApiResponse<List<DonHangResponseDTO>> getDonHangsByKhachHang(
            @PathVariable("maKhachHang") String maKhachHang) {
        return ApiResponse.<List<DonHangResponseDTO>>builder()
                .code(200)
                .message("Danh sách đơn hàng")
                .result(donHangService.getDonHangsByKhachHang(maKhachHang))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<DonHangDTO> addDonHang(@RequestBody DonHangDTO donHangDTO) {
        return ApiResponse.<DonHangDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(donHangService.addDonHang(donHangDTO))
                .build();
    }

}
