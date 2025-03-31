package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.entity.KhieuNaiEntity;

@Mapper(componentModel = "spring", uses = { LoaiKhieuNaiConverter.class,
        ChiTietDonHangConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KhieuNaiConverter {

    KhieuNaiConverter INSTANCE = Mappers.getMapper(KhieuNaiConverter.class);

    @Mapping(target = "maDonHang", source = "donHang.maDonHang")
    @Mapping(target = "maLoaiKhieuNai", source = "loaiKhieuNai.maLoaiKhieuNai")
    KhieuNaiDTO toDTO(KhieuNaiEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "trangThaiXuLy", defaultValue = "Chờ xử lý")
    @Mapping(target = "ngayKhieuNai", expression = "java(java.time.LocalDateTime.now())")
    KhieuNaiEntity toEntity(KhieuNaiDTO dto);

    @Mapping(target = "maKhieuNai", ignore = true)
    KhieuNaiEntity toEntity(KhieuNaiDTO khieuNaiDTO, @MappingTarget KhieuNaiEntity updatedKhieuNai);

}
