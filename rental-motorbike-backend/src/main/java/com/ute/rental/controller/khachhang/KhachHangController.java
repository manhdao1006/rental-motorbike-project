package com.ute.rental.controller.khachhang;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
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
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.dto.KhachHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.service.IKhachHangService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "khachHang" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/nguoi-mua")
public class KhachHangController {

    private final IKhachHangService khachHangService;

    @GetMapping("/{maNguoiDung}")
    public ApiResponse<KhachHangResponseDTO> getKhachHangByMaKhachHang(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<KhachHangResponseDTO>builder()
                .code(200)
                .message("Khách hàng với mã khách hàng là " + maNguoiDung)
                .result(khachHangService.getKhachHangByMaKhachHang(maNguoiDung))
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
    public ResponseEntity<String> deleteKhachHang(@PathVariable("maNguoiDung") String maNguoiDung) {
        khachHangService.deleteKhachHang(maNguoiDung);
        return ResponseEntity.ok("Xóa thành công");
    }

}
