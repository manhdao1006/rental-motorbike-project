package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ute.rental.entity.HopDongEntity;

public interface HopDongRepository extends JpaRepository<HopDongEntity, String> {

    List<HopDongEntity> findHopDongsByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(String maChuCuaHang, String trangThaiXoa);

    List<HopDongEntity> findHopDongsByKhachHang_MaKhachHangAndTrangThaiXoa(String maKhachHang, String trangThaiXoa);

    List<HopDongEntity> findHopDongsByTrangThaiXoa(String trangThaiXoa);

    Optional<HopDongEntity> findOneByMaHopDong(String maHopDong);

}
