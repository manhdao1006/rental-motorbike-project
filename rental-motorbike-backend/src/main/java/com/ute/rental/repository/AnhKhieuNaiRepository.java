package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhKhieuNaiEntity;

@Repository
public interface AnhKhieuNaiRepository extends JpaRepository<AnhKhieuNaiEntity, String> {

    Optional<AnhKhieuNaiEntity> findOneByTenAnh(String tenAnh);

    @Query("SELECT COUNT(akn) FROM AnhKhieuNaiEntity akn WHERE akn.maAnhKhieuNai LIKE :prefix%")
    int countByMaAnhKhieuNaiStartingWith(@Param("prefix") String prefix);

}
