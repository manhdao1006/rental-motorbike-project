package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.NguoiDungEntity;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDungEntity, String> {

    Optional<NguoiDungEntity> findByEmail(String email);

    Optional<NguoiDungEntity> findOneByMaNguoiDung(String maNguoiDung);

    Boolean existsByEmail(String email);

    @Query("SELECT COUNT(n) FROM NguoiDungEntity n WHERE n.maNguoiDung LIKE :prefix%")
    int countByMaNguoiDungStartingWith(@Param("prefix") String prefix);

    @Query("SELECT u FROM NguoiDungEntity u JOIN u.vaiTros r WHERE r.maVaiTro = ?1 AND u.trangThaiXoa = ?2")
    List<NguoiDungEntity> findNguoiDungsByVaiTro(String maVaiTro, String trangThaiXoa);

}
