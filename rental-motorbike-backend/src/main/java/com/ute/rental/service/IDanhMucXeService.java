package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.DanhMucXeDTO;

public interface IDanhMucXeService {

    List<DanhMucXeDTO> getDanhMucXes();

    DanhMucXeDTO addDanhMucXe(DanhMucXeDTO danhMucXeDTO);

    DanhMucXeDTO updateDanhMucXe(String maDanhMucXe, DanhMucXeDTO updatedDanhMucXe);

    void deleteDanhMucXe(String maDanhMucXe);

    DanhMucXeDTO getDanhMucXeByMaDanhMucXe(String maDanhMucXe);

    List<DanhMucXeDTO> getNavigations();

}
