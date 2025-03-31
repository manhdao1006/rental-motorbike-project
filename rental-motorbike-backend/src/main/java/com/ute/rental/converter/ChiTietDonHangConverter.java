package com.ute.rental.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.entity.ChiTietDonHangEntity;

@Mapper(componentModel = "spring", uses = { XeMayConverter.class,
        DonHangConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiTietDonHangConverter {

    ChiTietDonHangConverter INSTANCE = Mappers.getMapper(ChiTietDonHangConverter.class);

    @Mapping(target = "maDonHang", source = "donHang.maDonHang")
    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    ChiTietDonHangDTO toDTO(ChiTietDonHangEntity entity);

    @Mapping(target = "maDonHang", source = "donHang.maDonHang")
    @Mapping(target = "maXeMay", source = "xeMay.maXeMay")
    List<ChiTietDonHangDTO> toDTOs(List<ChiTietDonHangEntity> entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    ChiTietDonHangEntity toEntity(ChiTietDonHangDTO dto);

    @Mapping(target = "donHang.maDonHang", ignore = true)
    @Mapping(target = "xeMay.maXeMay", ignore = true)
    ChiTietDonHangEntity toEntity(ChiTietDonHangDTO chiTietDonHangDTO,
            @MappingTarget ChiTietDonHangEntity updatedChiTietDonHang);

}
