package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.LoaiKhieuNaiEntity;

@Repository
public interface LoaiKhieuNaiRepository extends JpaRepository<LoaiKhieuNaiEntity, String> {

    List<LoaiKhieuNaiEntity> findLoaiKhieuNaisByTrangThaiXoa(String trangThaiXoa);

    Optional<LoaiKhieuNaiEntity> findOneByMaLoaiKhieuNai(String maLoaiKhieuNai);

}
