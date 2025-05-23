package com.ute.rental.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.entity.DonHangEntity;

@Mapper(componentModel = "spring", uses = { ChuCuaHangConverter.class,
        KhachHangConverter.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DonHangConverter {

    DonHangConverter INSTANCE = Mappers.getMapper(DonHangConverter.class);

    @Mapping(target = "maKhachHang", source = "khachHang.maKhachHang")
    @Mapping(target = "maNhanVien", source = "nhanVien.maNhanVien")
    DonHangDTO toDTO(DonHangEntity entity);

    @Mapping(target = "trangThaiXoa", defaultValue = "1")
    @Mapping(target = "ngayTao", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "trangThaiDonHang", defaultValue = "Chờ xử lý")
    @Mapping(target = "trangThaiThanhToan", defaultValue = "Chưa thanh toán")
    DonHangEntity toEntity(DonHangDTO dto);

    @Mapping(target = "maDonHang", ignore = true)
    DonHangEntity toEntity(DonHangDTO donHangDTO, @MappingTarget DonHangEntity updatedDonHang);

}
