package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.entity.ChuCuaHangEntity;

@Mapper(componentModel = "spring", uses = {
        PhuongXaConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChuCuaHangConverter {

    ChuCuaHangConverter INSTANCE = Mappers.getMapper(ChuCuaHangConverter.class);

    @Mapping(target = "maPhuongXa", source = "phuongXa.maPhuongXa")
    ChuCuaHangDTO toDTO(ChuCuaHangEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    ChuCuaHangEntity toEntity(ChuCuaHangDTO dto);

    @Mapping(target = "maChuCuaHang", ignore = true)
    ChuCuaHangEntity toEntity(ChuCuaHangDTO chuCuaHangDTO,
            @MappingTarget ChuCuaHangEntity updatedChuCuaHang);

}
