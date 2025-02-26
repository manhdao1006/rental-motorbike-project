package com.ute.rental.controller.nhanvien;

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
import com.ute.rental.dto.HopDongDTO;
import com.ute.rental.dto.HopDongResponseDTO;
import com.ute.rental.service.IHopDongService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "hopDong" + ApiName.API)
@RequestMapping(ApiUrl.API_NHANVIEN + "/hop-dong")
public class HopDongController {

    private final IHopDongService hopDongService;

    @GetMapping("/list")
    public ApiResponse<List<HopDongResponseDTO>> getHopDongs() {
        return ApiResponse.<List<HopDongResponseDTO>>builder()
                .code(200)
                .message("Danh sách hợp đồng")
                .result(hopDongService.getHopDongs())
                .build();
    }

    @GetMapping("/maHopDong={maHopDong}")
    public ApiResponse<HopDongResponseDTO> getHopDongByMaHopDong(@PathVariable("maHopDong") String maHopDong) {
        return ApiResponse.<HopDongResponseDTO>builder()
                .code(200)
                .message("Hợp đồng với mã hợp đồng " + maHopDong)
                .result(hopDongService.getHopDongByMaHopDong(maHopDong))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<HopDongDTO> addHopDong(@ModelAttribute HopDongDTO hopDongDTO,
            @RequestParam(value = "anhHopDongList", required = false) List<MultipartFile> anhHopDongList)
            throws IOException {
        return ApiResponse.<HopDongDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(hopDongService.addHopDong(hopDongDTO, anhHopDongList))
                .build();
    }

    @PutMapping("edit/{maHopDong}")
    public ApiResponse<HopDongDTO> updateHopDong(@PathVariable("maHopDong") String maHopDong,
            @ModelAttribute HopDongDTO hopDongDTO,
            @RequestParam(value = "anhHopDongList", required = false) List<MultipartFile> anhHopDongList,
            @RequestParam(value = "deletedHopDongs", required = false) List<String> deletedHopDongs)
            throws IOException {
        return ApiResponse.<HopDongDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(hopDongService.updateHopDong(maHopDong, hopDongDTO, anhHopDongList, deletedHopDongs))
                .build();
    }

    @PutMapping("delete/{maHopDong}")
    public ApiResponse<String> deleteHopDong(@PathVariable("maHopDong") String maHopDong) {
        hopDongService.deleteHopDong(maHopDong);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }

}
