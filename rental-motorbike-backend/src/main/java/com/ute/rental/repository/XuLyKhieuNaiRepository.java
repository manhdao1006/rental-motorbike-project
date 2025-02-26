package com.ute.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.XuLyKhieuNaiEntity;

@Repository
public interface XuLyKhieuNaiRepository extends JpaRepository<XuLyKhieuNaiEntity, String> {

}
