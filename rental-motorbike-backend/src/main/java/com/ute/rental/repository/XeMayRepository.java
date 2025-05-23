package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.XeMayEntity;

@Repository
public interface XeMayRepository extends JpaRepository<XeMayEntity, String> {

    List<XeMayEntity> findXeMaysByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(String maChuCuaHang, String trangThaiXoa);

    List<XeMayEntity> findXeMaysByTrangThaiHoatDongAndTrangThaiXoa(String trangThaiHoatDong, String trangThaiXoa);

    Optional<XeMayEntity> findOneByMaXeMay(String maXeMay);

    @Query("SELECT COUNT(xm) FROM XeMayEntity xm WHERE xm.maXeMay LIKE :prefix%")
    int countByMaXeMayStartingWith(@Param("prefix") String prefix);

    List<XeMayEntity> findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
            String maQuanHuyen, String trangThaiXoa, String trangThaiHoatDong);

    List<XeMayEntity> findXeMaysByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByMaXeMayDesc(
            String maQuanHuyen, String trangThaiXoa, String trangThaiHoatDong);

}
