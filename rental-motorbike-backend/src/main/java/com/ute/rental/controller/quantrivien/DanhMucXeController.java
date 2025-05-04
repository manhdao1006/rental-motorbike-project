package com.ute.rental.controller.quantrivien;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;
import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.DanhMucXeDTO;
import com.ute.rental.service.IDanhMucXeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "danhMucXe" + ApiName.API)
@RequestMapping(ApiUrl.API_QUANTRIVIEN + "/danh-muc-xe")
public class DanhMucXeController {

    private final IDanhMucXeService danhMucXeService;

    @PostMapping("/add")
    public ApiResponse<DanhMucXeDTO> addDanhMucXe(@RequestBody DanhMucXeDTO danhMucXeDTO) {
        return ApiResponse.<DanhMucXeDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(danhMucXeService.addDanhMucXe(danhMucXeDTO))
                .build();
    }

    @PutMapping("edit/{maDanhMucXe}")
    public ApiResponse<DanhMucXeDTO> updateDanhMucXe(@PathVariable("maDanhMucXe") String maDanhMucXe,
            @RequestBody DanhMucXeDTO danhMucXeDTO) {
        return ApiResponse.<DanhMucXeDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(danhMucXeService.updateDanhMucXe(maDanhMucXe, danhMucXeDTO))
                .build();
    }

    @PutMapping("/delete/{maDanhMucXe}")
    public ApiResponse<String> deleteDanhMucXe(@PathVariable("maDanhMucXe") String maDanhMucXe) {
        danhMucXeService.deleteDanhMucXe(maDanhMucXe);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
