package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhXeMayEntity;

@Repository
public interface AnhXeMayRepository extends JpaRepository<AnhXeMayEntity, String> {

    Optional<AnhXeMayEntity> findOneByTenAnh(String tenAnh);

    @Query("SELECT COUNT(axm) FROM AnhXeMayEntity axm WHERE axm.maAnhXeMay LIKE :prefix%")
    int countByMaAnhXeMayStartingWith(@Param("prefix") String prefix);

}
