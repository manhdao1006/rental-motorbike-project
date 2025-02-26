package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.AnhCaVetXeEntity;

@Repository
public interface AnhCaVetXeRepository extends JpaRepository<AnhCaVetXeEntity, String> {

    Optional<AnhCaVetXeEntity> findOneByTenAnh(String tenAnh);

}
