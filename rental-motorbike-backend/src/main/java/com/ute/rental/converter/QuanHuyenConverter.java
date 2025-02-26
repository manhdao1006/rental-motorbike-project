package com.ute.rental.converter;

import org.mapstruct.Mapper;

import com.ute.rental.dto.QuanHuyenDTO;
import com.ute.rental.entity.QuanHuyenEntity;

@Mapper(componentModel = "spring", uses = { ChuCuaHangConverter.class })
public interface QuanHuyenConverter {
    QuanHuyenDTO toDTO(QuanHuyenEntity entity);
}
