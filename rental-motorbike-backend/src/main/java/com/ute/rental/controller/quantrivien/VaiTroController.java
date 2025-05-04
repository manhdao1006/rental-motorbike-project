package com.ute.rental.controller.quantrivien;

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
import com.ute.rental.dto.VaiTroDTO;
import com.ute.rental.service.IVaiTroService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "vaiTro" + ApiName.API)
@RequestMapping(ApiUrl.API_QUANTRIVIEN + "/vai-tro")
public class VaiTroController {

    private final IVaiTroService vaiTroService;

    @GetMapping("/list")
    public ApiResponse<List<VaiTroDTO>> getVaiTros() {
        return ApiResponse.<List<VaiTroDTO>>builder()
                .code(200)
                .message("Danh sách vai trò")
                .result(vaiTroService.getVaiTros())
                .build();
    }

    @GetMapping("/maVaiTro/{maVaiTro}")
    public ApiResponse<VaiTroDTO> getVaiTroByMaVaiTro(@PathVariable("maVaiTro") String maVaiTro) {
        return ApiResponse.<VaiTroDTO>builder()
                .code(200)
                .message("Vai trò với mã là " + maVaiTro)
                .result(vaiTroService.getVaiTroByMaVaiTro(maVaiTro))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<VaiTroDTO> addVaiTro(@RequestBody VaiTroDTO vaiTroDTO) {
        return ApiResponse.<VaiTroDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(vaiTroService.addVaiTro(vaiTroDTO))
                .build();
    }

    @PutMapping("/edit/{maVaiTro}")
    public ApiResponse<VaiTroDTO> updateVaiTro(@PathVariable("maVaiTro") String maVaiTro,
            @RequestBody VaiTroDTO vaiTroDTO) {
        return ApiResponse.<VaiTroDTO>builder()
                .code(200)
                .message("Cập nhật thành công")
                .result(vaiTroService.updateVaiTro(maVaiTro, vaiTroDTO))
                .build();
    }
}
