package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.PhuongXaDTO;

public interface IPhuongXaService {

    List<PhuongXaDTO> getPhuongXas();

    List<PhuongXaDTO> getPhuongXasByMaQuanHuyen(String maQuanHuyen);

}
