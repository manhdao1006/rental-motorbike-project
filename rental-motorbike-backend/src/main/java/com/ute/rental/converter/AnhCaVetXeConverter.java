package com.ute.rental.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.AnhCaVetXeDTO;
import com.ute.rental.entity.AnhCaVetXeEntity;

@Mapper(componentModel = "spring", uses = {
        XeMayConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnhCaVetXeConverter {

    AnhCaVetXeConverter INSTANCE = Mappers.getMapper(AnhCaVetXeConverter.class);

    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    AnhCaVetXeDTO toDTO(AnhCaVetXeEntity entity);

    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    List<AnhCaVetXeDTO> toDTOs(List<AnhCaVetXeEntity> entity);

    AnhCaVetXeEntity toEntity(AnhCaVetXeDTO dto);

    @Mapping(target = "maAnhCaVet", ignore = true)
    AnhCaVetXeEntity toEntity(AnhCaVetXeDTO anhDTO, @MappingTarget AnhCaVetXeEntity updatedAnh);

}
