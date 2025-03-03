package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.ChiTietDonHangEntity;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHangEntity, String> {

    Optional<ChiTietDonHangEntity> findOneByMaChiTietDonHang(String maChiTietDonHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_MaDonHangAndTrangThaiXoa(String maDonHang,
            String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByTrangThaiXoa(String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHang(String maKhachHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHang(
            String maKhachHang, String trangThaiDonHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_ChuCuaHang_MaChuCuaHang(String maChuCuaHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_ChuCuaHang_MaChuCuaHangAndDonHang_TrangThaiDonHang(
            String maChuCuaHang, String trangThaiDonHang);

    @Query("SELECT COUNT(ctdh) FROM ChiTietDonHangEntity ctdh WHERE ctdh.maChiTietDonHang LIKE :prefix%")
    int countByMaChiTietDonHangStartingWith(@Param("prefix") String prefix);

}
