package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.KhieuNaiEntity;

@Repository
public interface KhieuNaiRepository extends JpaRepository<KhieuNaiEntity, String> {

    List<KhieuNaiEntity> findKhieuNaisByTrangThaiXoa(String trangThaiXoa);

    List<KhieuNaiEntity> findKhieuNaisByTrangThaiXoaAndLoaiKhieuNai_MaLoaiKhieuNai(String trangThaiXoa,
            String maLoaiKhieuNai);

    Optional<KhieuNaiEntity> findOneByMaKhieuNai(String maKhieuNai);

    @Query("SELECT COUNT(kn) FROM KhieuNaiEntity kn WHERE kn.maKhieuNai LIKE :prefix%")
    int countByMaKhieuNaiStartingWith(@Param("prefix") String prefix);

    List<KhieuNaiEntity> findByTrangThaiXoaAndChiTietDonHang_DonHang_KhachHang_MaKhachHang(String trangThaiXoa,
            String maKhachHang);

    List<KhieuNaiEntity> findByTrangThaiXoaAndChiTietDonHang_DonHang_ChuCuaHang_MaChuCuaHang(String trangThaiXoa,
            String maChuCuaHang);

}
