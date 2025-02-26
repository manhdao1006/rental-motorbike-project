package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.XeMayDTO;
import com.ute.rental.entity.XeMayEntity;

@Mapper(componentModel = "spring", uses = {
        DanhMucXeConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface XeMayConverter {

    XeMayConverter INSTANCE = Mappers.getMapper(XeMayConverter.class);

    @Mapping(target = "maDanhMucXe", source = "danhMucXe.maDanhMucXe")
    @Mapping(target = "maChuCuaHang", source = "chuCuaHang.maChuCuaHang")
    XeMayDTO toDTO(XeMayEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "trangThaiHoatDong", defaultValue = "Chờ duyệt")
    XeMayEntity toEntity(XeMayDTO dto);

    @Mapping(target = "maXeMay", ignore = true)
    XeMayEntity toEntity(XeMayDTO xeMayDTO, @MappingTarget XeMayEntity updatedXeMay);

}
