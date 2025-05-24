package com.ute.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.TinNhanEntity;

@Repository
public interface TinNhanRepository extends JpaRepository<TinNhanEntity, String> {

    List<TinNhanEntity> findByHoiThoai_MaHoiThoaiOrderByThoiGianGuiAsc(String maHoiThoai);

    @Query("SELECT COUNT(tn) FROM TinNhanEntity tn WHERE tn.maTinNhan LIKE :prefix%")
    int countByMaTinNhanStartingWith(@Param("prefix") String prefix);

}
