package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.entity.NguoiDungEntity;

@Mapper(componentModel = "spring", uses = { VaiTroConverter.class,
        NhanVienConverter.class,
        KhachHangConverter.class,
        ChuCuaHangConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NguoiDungConverter {

    NguoiDungConverter INSTANCE = Mappers.getMapper(NguoiDungConverter.class);

    @Mapping(target = "vaiTros", ignore = true)
    @Mapping(target = "maKhachHang", source = "khachHang.maKhachHang")
    @Mapping(target = "maChuCuaHang", source = "chuCuaHang.maChuCuaHang")
    @Mapping(target = "maNhanVien", source = "nhanVien.maNhanVien")
    NguoiDungDTO toDTO(NguoiDungEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "ngayDangKy", expression = "java(java.time.LocalDateTime.now())")
    NguoiDungEntity toEntity(NguoiDungDTO dto);

    @Mapping(target = "maNguoiDung", ignore = true)
    NguoiDungEntity toEntity(NguoiDungDTO nguoiDungDTO, @MappingTarget NguoiDungEntity updatedNguoiDung);
}
