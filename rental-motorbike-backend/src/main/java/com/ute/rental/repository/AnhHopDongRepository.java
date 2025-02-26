package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhHopDongEntity;

@Repository
public interface AnhHopDongRepository extends JpaRepository<AnhHopDongEntity, String> {

    Optional<AnhHopDongEntity> findOneByTenAnh(String tenAnh);

}
