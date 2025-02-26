package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.KhachHangEntity;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHangEntity, String> {

    List<KhachHangEntity> findKhachHangsByTrangThaiXoa(String trangThaiXoa);

    Optional<KhachHangEntity> findOneByMaKhachHang(String maNguoiDung);

}
