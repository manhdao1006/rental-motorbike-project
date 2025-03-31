package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.NhanVienEntity;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVienEntity, String> {

    List<NhanVienEntity> findNhanViensByTrangThaiXoa(String trangThaiXoa);

    Optional<NhanVienEntity> findOneByMaNhanVien(String maNguoiDung);

    List<NhanVienEntity> findNhanViensByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(String maChuCuaHang,
            String trangThaiXoa);

}
