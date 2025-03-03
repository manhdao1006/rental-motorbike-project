package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.DanhMucXeEntity;

@Repository
public interface DanhMucXeRepository extends JpaRepository<DanhMucXeEntity, String> {

    Optional<DanhMucXeEntity> findOneByMaDanhMucXe(String maDanhMucXe);

    List<DanhMucXeEntity> findDanhMucsByTrangThaiXoa(String trangThaiXoa);

    List<DanhMucXeEntity> findFirst6ByTrangThaiXoaOrderByMaDanhMucXeAsc(String trangThaiXoa);

    @Query("SELECT COUNT(dmx) FROM DanhMucXeEntity dmx WHERE dmx.maDanhMucXe LIKE :prefix%")
    int countByMaDanhMucXeStartingWith(@Param("prefix") String prefix);

}
