package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.QuanHuyenDTO;

public interface IQuanHuyenService {

    List<QuanHuyenDTO> getQuanHuyens();

    QuanHuyenDTO getQuanHuyenByMaPhuongXa(String maPhuongXa);

}
