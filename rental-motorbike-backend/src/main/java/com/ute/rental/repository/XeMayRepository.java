package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.XeMayEntity;

@Repository
public interface XeMayRepository extends JpaRepository<XeMayEntity, String> {

    List<XeMayEntity> findXeMaysByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(String maChuCuaHang, String trangThaiXoa);

    List<XeMayEntity> findXeMaysByTrangThaiXoa(String trangThaiXoa);

    Optional<XeMayEntity> findOneByMaXeMay(String maXeMay);

}
