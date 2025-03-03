package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.XuLyKhieuNaiEntity;

@Repository
public interface XuLyKhieuNaiRepository extends JpaRepository<XuLyKhieuNaiEntity, String> {

    @Query("SELECT COUNT(xlkn) FROM XuLyKhieuNaiEntity xlkn WHERE xlkn.maXuLyKhieuNai LIKE :prefix%")
    int countByMaXuLyKhieuNaiStartingWith(@Param("prefix") String prefix);

    List<XuLyKhieuNaiEntity> findXuLyKhieuNaisByTrangThaiXoa(String trangThaiXoa);

    List<XuLyKhieuNaiEntity> findXuLyKhieuNaisByTrangThaiXoaAndNhanVien_MaNhanVien(String trangThaiXoa,
            String maNhanVien);

    Optional<XuLyKhieuNaiEntity> findOneByMaXuLyKhieuNai(String maXuLyKhieuNai);

}
