package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.HopDongDTO;
import com.ute.rental.entity.HopDongEntity;

@Mapper(componentModel = "spring", uses = { KhachHangConverter.class,
        ChuCuaHangConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HopDongConverter {

    HopDongConverter INSTANCE = Mappers.getMapper(HopDongConverter.class);

    @Mapping(target = "maKhachHang", source = "khachHang.maKhachHang")
    @Mapping(target = "maChuCuaHang", source = "chuCuaHang.maChuCuaHang")
    HopDongDTO toDTO(HopDongEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "ngayTao", expression = "java(java.time.LocalDateTime.now())")
    HopDongEntity toEntity(HopDongDTO dto);

    @Mapping(target = "maHopDong", ignore = true)
    HopDongEntity toEntity(HopDongDTO hopDongDTO, @MappingTarget HopDongEntity updatedHopDong);

}
