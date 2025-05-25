package com.ute.rental.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.AnhKhieuNaiDTO;
import com.ute.rental.entity.AnhKhieuNaiEntity;

@Mapper(componentModel = "spring", uses = {
        KhieuNaiConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnhKhieuNaiConverter {

    AnhKhieuNaiConverter INSTANCE = Mappers.getMapper(AnhKhieuNaiConverter.class);

    @Mapping(target = "maKhieuNai", source = "khieuNai.maKhieuNai")
    AnhKhieuNaiDTO toDTO(AnhKhieuNaiEntity entity);

    @Mapping(target = "maKhieuNai", source = "khieuNai.maKhieuNai")
    List<AnhKhieuNaiDTO> toDTOs(List<AnhKhieuNaiEntity> entities);

    AnhKhieuNaiEntity toEntity(AnhKhieuNaiDTO dto);

    @Mapping(target = "maAnhKhieuNai", ignore = true)
    AnhKhieuNaiEntity toEntity(AnhKhieuNaiDTO anhDTO, @MappingTarget AnhKhieuNaiEntity updatedAnh);

}
