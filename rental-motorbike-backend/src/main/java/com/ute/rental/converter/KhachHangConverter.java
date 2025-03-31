package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.entity.KhachHangEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KhachHangConverter {

    KhachHangConverter INSTANCE = Mappers.getMapper(KhachHangConverter.class);

    KhachHangDTO toDTO(KhachHangEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    KhachHangEntity toEntity(KhachHangDTO dto);

    @Mapping(target = "maKhachHang", ignore = true)
    KhachHangEntity toEntity(KhachHangDTO khachHangDTO, @MappingTarget KhachHangEntity updatedKhachHang);

}
