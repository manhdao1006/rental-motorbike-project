package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnhHopDongDTO {

    private String maAnhHopDong;
    private String tenAnh;
    private String duongDan;
    private String maHopDong;

}
