package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.VaiTroDTO;

public interface IVaiTroService {

    List<VaiTroDTO> getVaiTros();

    VaiTroDTO addVaiTro(VaiTroDTO vaiTroDTO);

    VaiTroDTO updateVaiTro(String maVaiTro, VaiTroDTO vaiTroDTO);

    VaiTroDTO getVaiTroByMaVaiTro(String maVaiTro);

}
