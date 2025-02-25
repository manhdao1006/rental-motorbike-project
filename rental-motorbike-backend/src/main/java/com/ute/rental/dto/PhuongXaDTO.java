package com.ute.rental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhuongXaDTO {

    private String maPhuongXa;
    private String tenPhuongXa;
    private String maQuanHuyen;
    private List<ChuCuaHangDTO> chuCuaHangs;

}
