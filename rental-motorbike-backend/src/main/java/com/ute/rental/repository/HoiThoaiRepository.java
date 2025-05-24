package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.HoiThoaiEntity;

@Repository
public interface HoiThoaiRepository extends JpaRepository<HoiThoaiEntity, String> {

    Optional<HoiThoaiEntity> findOneByMaHoiThoai(String maHoiThoai);

    Optional<HoiThoaiEntity> findByKhachHang_MaNguoiDungAndChuCuaHang_MaNguoiDung(String maKhachHang,
            String maChuCuaHang);

    List<HoiThoaiEntity> findByKhachHang_MaNguoiDung(String maKhachHang);

    List<HoiThoaiEntity> findByChuCuaHang_MaNguoiDung(String maKhachHang);

}
