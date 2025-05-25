package com.ute.rental.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.AnhXeMayDTO;
import com.ute.rental.entity.AnhXeMayEntity;

@Mapper(componentModel = "spring", uses = {
        XeMayConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnhXeMayConverter {

    AnhXeMayConverter INSTANCE = Mappers.getMapper(AnhXeMayConverter.class);

    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    AnhXeMayDTO toDTO(AnhXeMayEntity entity);

    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    List<AnhXeMayDTO> toDTOs(List<AnhXeMayEntity> entities);

    AnhXeMayEntity toEntity(AnhXeMayDTO dto);

    @Mapping(target = "maAnhXeMay", ignore = true)
    AnhXeMayEntity toEntity(AnhXeMayDTO anhDTO, @MappingTarget AnhXeMayEntity updatedAnh);

}
