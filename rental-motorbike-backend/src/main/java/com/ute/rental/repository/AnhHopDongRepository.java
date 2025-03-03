package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhHopDongEntity;

@Repository
public interface AnhHopDongRepository extends JpaRepository<AnhHopDongEntity, String> {

    Optional<AnhHopDongEntity> findOneByTenAnh(String tenAnh);

    @Query("SELECT COUNT(ahd) FROM AnhHopDongEntity ahd WHERE ahd.maAnhHopDong LIKE :prefix%")
    int countByMaAnhHopDongStartingWith(@Param("prefix") String prefix);

}
