package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.XuLyKhieuNaiDTO;
import com.ute.rental.entity.XuLyKhieuNaiEntity;

@Mapper(componentModel = "spring", uses = { NhanVienConverter.class,
        KhieuNaiConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface XuLyKhieuNaiConverter {

    XuLyKhieuNaiConverter INSTANCE = Mappers.getMapper(XuLyKhieuNaiConverter.class);

    @Mapping(target = "maNhanVien", source = "nhanVien.maNhanVien")
    @Mapping(target = "maKhieuNai", source = "khieuNai.maKhieuNai")
    XuLyKhieuNaiDTO toDTO(XuLyKhieuNaiEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "ngayXuLy", expression = "java(java.time.LocalDateTime.now())")
    XuLyKhieuNaiEntity toEntity(XuLyKhieuNaiDTO dto);

    @Mapping(target = "maXuLyKhieuNai", ignore = true)
    XuLyKhieuNaiEntity toEntity(XuLyKhieuNaiDTO xuLyKhieuNaiDTO, @MappingTarget XuLyKhieuNaiEntity updatedXuLyKhieuNai);

}
