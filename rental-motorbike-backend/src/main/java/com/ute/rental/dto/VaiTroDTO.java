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
public class VaiTroDTO {

    private String maVaiTro;
    private String tenVaiTro;
    private List<NguoiDungDTO> nguoiDungs;

}
