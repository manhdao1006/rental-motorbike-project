package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.KhieuNaiEntity;

@Repository
public interface KhieuNaiRepository extends JpaRepository<KhieuNaiEntity, String> {

    List<KhieuNaiEntity> findKhieuNaisByTrangThaiXoa(String trangThaiXoa);

    List<KhieuNaiEntity> findKhieuNaisByTrangThaiXoaAndLoaiKhieuNai_MaLoaiKhieuNai(String trangThaiXoa,
            String maLoaiKhieuNai);

    Optional<KhieuNaiEntity> findOneByMaKhieuNai(String maKhieuNai);

}
