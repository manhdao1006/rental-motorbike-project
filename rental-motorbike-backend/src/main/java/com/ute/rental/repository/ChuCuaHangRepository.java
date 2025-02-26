package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.ChuCuaHangEntity;

@Repository
public interface ChuCuaHangRepository extends JpaRepository<ChuCuaHangEntity, String> {

    Optional<ChuCuaHangEntity> findOneByMaChuCuaHang(String maNguoiDung);

    List<ChuCuaHangEntity> findChuCuaHangsByTrangThaiXoa(String string);

}
