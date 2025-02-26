package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.DanhMucXeDTO;
import com.ute.rental.entity.DanhMucXeEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DanhMucXeConverter {

    DanhMucXeConverter INSTANCE = Mappers.getMapper(DanhMucXeConverter.class);

    DanhMucXeDTO toDTO(DanhMucXeEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    DanhMucXeEntity toEntity(DanhMucXeDTO dto);

    @Mapping(target = "maDanhMucXe", ignore = true)
    DanhMucXeEntity toEntity(DanhMucXeDTO danhMucXeDTO, @MappingTarget DanhMucXeEntity updatedDanhMucXe);

}
