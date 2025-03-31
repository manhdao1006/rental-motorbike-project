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
import com.ute.rental.dto.XeMayDTO;
import com.ute.rental.dto.XeMayResponseDTO;
import com.ute.rental.service.IXeMayService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "xeMay" + ApiName.WEB)
@RequestMapping(ApiUrl.API_CHUCUAHANG + "/xe-may")
public class XeMayController {

    private final IXeMayService xeMayService;

    @GetMapping("/chu-cua-hang/{maChuCuaHang}")
    public ApiResponse<List<XeMayResponseDTO>> getXeMaysByMaChuCuaHang(
            @PathVariable("maChuCuaHang") String maChuCuaHang) {
        return ApiResponse.<List<XeMayResponseDTO>>builder()
                .code(200)
                .message("Danh sách xe máy")
                .result(xeMayService.getXeMaysByMaChuCuaHang(maChuCuaHang))
                .build();
    }

    @GetMapping("/maXeMay={maXeMay}")
    public ApiResponse<XeMayResponseDTO> getXeMayByMaXeMay(@PathVariable("maXeMay") String maXeMay) {
        return ApiResponse.<XeMayResponseDTO>builder()
                .code(200)
                .message("Xe máy với mã xe máy " + maXeMay)
                .result(xeMayService.getXeMayByMaXeMay(maXeMay))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<XeMayDTO> addXeMay(@ModelAttribute XeMayDTO xeMayDTO,
            @RequestParam(value = "anhXeMayList", required = false) List<MultipartFile> anhXeMayList)
            throws IOException {
        return ApiResponse.<XeMayDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(xeMayService.addXeMay(xeMayDTO, anhXeMayList))
                .build();
    }

    @PutMapping("edit/{maXeMay}")
    public ApiResponse<XeMayDTO> updateXeMay(@PathVariable("maXeMay") String maXeMay,
            @ModelAttribute XeMayDTO xeMayDTO,
            @RequestParam(value = "anhXeMayList", required = false) List<MultipartFile> anhXeMayList,
            @RequestParam(value = "deletedAnhXeMays", required = false) List<String> deletedAnhXeMays)
            throws IOException {
        return ApiResponse.<XeMayDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(xeMayService.updateXeMay(maXeMay, xeMayDTO, anhXeMayList, deletedAnhXeMays))
                .build();
    }

    @PutMapping("delete/{maXeMay}")
    public ApiResponse<String> deleteXeMay(@PathVariable("maXeMay") String maXeMay) {
        xeMayService.deleteXeMay(maXeMay);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
