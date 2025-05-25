package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnhKhieuNaiDTO {

    private String maAnhKhieuNai;
    private String tenAnh;
    private String duongDan;
    private String maKhieuNai;

}
