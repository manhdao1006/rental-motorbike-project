package com.ute.rental.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.AnhHopDongDTO;
import com.ute.rental.entity.AnhHopDongEntity;

@Mapper(componentModel = "spring", uses = {
        HopDongConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnhHopDongConverter {

    AnhHopDongConverter INSTANCE = Mappers.getMapper(AnhHopDongConverter.class);

    @Mapping(target = "maHopDong", source = "hopDong.maHopDong")
    AnhHopDongDTO toDTO(AnhHopDongEntity entity);

    @Mapping(target = "maHopDong", source = "xeMay.maHopDong")
    List<AnhHopDongDTO> toDTOs(List<AnhHopDongEntity> entity);

    AnhHopDongEntity toEntity(AnhHopDongDTO dto);

    @Mapping(target = "maAnhHopDong", ignore = true)
    AnhHopDongEntity toEntity(AnhHopDongDTO anhDTO, @MappingTarget AnhHopDongEntity updatedAnh);

}
