package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.PhuongXaEntity;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXaEntity, String> {

    Optional<PhuongXaEntity> findOneByMaPhuongXa(String maPhuongXa);

    List<PhuongXaEntity> findPhuongXasByQuanHuyen_MaQuanHuyen(String maQuanHuyen);

}
