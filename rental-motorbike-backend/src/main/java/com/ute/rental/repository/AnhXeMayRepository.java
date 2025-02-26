package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhXeMayEntity;

@Repository
public interface AnhXeMayRepository extends JpaRepository<AnhXeMayEntity, String> {

    Optional<AnhXeMayEntity> findOneByTenAnh(String tenAnh);

}
