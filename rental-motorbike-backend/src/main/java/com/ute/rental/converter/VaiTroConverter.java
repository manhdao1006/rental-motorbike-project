package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ute.rental.dto.VaiTroDTO;
import com.ute.rental.entity.VaiTroEntity;

@Mapper(componentModel = "spring")
public interface VaiTroConverter {
    @Mapping(target = "nguoiDungs", ignore = true)
    VaiTroDTO toDTO(VaiTroEntity entity);
}
