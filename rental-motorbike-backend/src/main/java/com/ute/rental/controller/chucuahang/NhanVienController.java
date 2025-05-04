package com.ute.rental.controller.chucuahang;

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
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NhanVienDTO;
import com.ute.rental.dto.NhanVienResponseDTO;
import com.ute.rental.service.INhanVienService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "nhanVien" + ApiName.API)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/nhan-vien")
public class NhanVienController {

    private final INhanVienService nhanVienService;

    @GetMapping("/maChuCuaHang/{maChuCuaHang}")
    public ApiResponse<List<NhanVienResponseDTO>> getNhanViensByChuCuaHang(
            @PathVariable("maChuCuaHang") String maChuCuaHang) {
        return ApiResponse.<List<NhanVienResponseDTO>>builder()
                .code(200)
                .message("Danh sách nhân viên")
                .result(nhanVienService.getNhanViensByChuCuaHang(maChuCuaHang))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<NhanVienResponseDTO>> getNhanViens() {
        return ApiResponse.<List<NhanVienResponseDTO>>builder()
                .code(200)
                .message("Danh sách nhân viên")
                .result(nhanVienService.getNhanViens())
                .build();
    }

    @GetMapping("/maNhanVien/{maNguoiDung}")
    public ApiResponse<NhanVienResponseDTO> getNhanVienByMaNhanVien(@PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<NhanVienResponseDTO>builder()
                .code(200)
                .message("Nhân viên với mã nhân viên là " + maNguoiDung)
                .result(nhanVienService.getNhanVienByMaNhanVien(maNguoiDung))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<NhanVienResponseDTO> addNhanVien(
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute NhanVienDTO nhanVienDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<NhanVienResponseDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(nhanVienService.addNhanVien(nguoiDungDTO, nhanVienDTO, file))
                .build();
    }

    @PutMapping("/edit/{maNguoiDung}")
    public ApiResponse<NhanVienResponseDTO> updateNhanVien(@PathVariable("maNguoiDung") String maNguoiDung,
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute NhanVienDTO nhanVienDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<NhanVienResponseDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(nhanVienService.updateNhanVien(maNguoiDung, nguoiDungDTO, nhanVienDTO, file))
                .build();
    }

    @PutMapping("delete/{maNguoiDung}")
    public ApiResponse<String> deleteNhanVien(@PathVariable("maNguoiDung") String maNguoiDung) {
        nhanVienService.deleteNhanVien(maNguoiDung);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công!")
                .build();
    }

}
