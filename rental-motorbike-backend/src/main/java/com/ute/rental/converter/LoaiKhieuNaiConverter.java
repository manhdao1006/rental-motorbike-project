package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.entity.LoaiKhieuNaiEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LoaiKhieuNaiConverter {

    LoaiKhieuNaiConverter INSTANCE = Mappers.getMapper(LoaiKhieuNaiConverter.class);

    LoaiKhieuNaiDTO toDTO(LoaiKhieuNaiEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    LoaiKhieuNaiEntity toEntity(LoaiKhieuNaiDTO dto);

    @Mapping(target = "maLoaiKhieuNai", ignore = true)
    LoaiKhieuNaiEntity toEntity(LoaiKhieuNaiDTO loaiKhieuNaiDTO, @MappingTarget LoaiKhieuNaiEntity updatedLoaiKhieuNai);

}
