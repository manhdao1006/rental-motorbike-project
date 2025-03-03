package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.VaiTroDTO;
import com.ute.rental.entity.VaiTroEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VaiTroConverter {

    VaiTroConverter INSTANCE = Mappers.getMapper(VaiTroConverter.class);

    @Mapping(target = "nguoiDungs", ignore = true)
    VaiTroDTO toDTO(VaiTroEntity entity);

    VaiTroEntity toEntity(VaiTroDTO dto);

    @Mapping(target = "maVaiTro", ignore = true)
    VaiTroEntity toEntity(VaiTroDTO vaiTroDTO, @MappingTarget VaiTroEntity updatedVaiTro);
}
