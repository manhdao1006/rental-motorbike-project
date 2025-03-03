package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ute.rental.entity.HopDongEntity;

public interface HopDongRepository extends JpaRepository<HopDongEntity, String> {

    List<HopDongEntity> findHopDongsByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(String maChuCuaHang, String trangThaiXoa);

    List<HopDongEntity> findHopDongsByKhachHang_MaKhachHangAndTrangThaiXoa(String maKhachHang, String trangThaiXoa);

    List<HopDongEntity> findHopDongsByTrangThaiXoa(String trangThaiXoa);

    Optional<HopDongEntity> findOneByMaHopDong(String maHopDong);

    @Query("SELECT COUNT(hd) FROM HopDongEntity hd WHERE hd.maHopDong LIKE :prefix%")
    int countByMaHopDongStartingWith(@Param("prefix") String prefix);

}
