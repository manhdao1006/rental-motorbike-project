package com.ute.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnhCaVetXeDTO {

    private String maAnhCaVet;
    private String tenAnhId;
    private String tenAnh;
    private String maXeMay;

}
