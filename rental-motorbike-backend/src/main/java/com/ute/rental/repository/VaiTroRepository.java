package com.ute.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.VaiTroEntity;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTroEntity, String> {

    Optional<VaiTroEntity> findOneByTenVaiTro(String tenVaiTro);

    Optional<VaiTroEntity> findOneByMaVaiTro(String maVaiTro);

}
