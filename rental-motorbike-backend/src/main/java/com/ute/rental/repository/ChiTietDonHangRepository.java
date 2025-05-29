package com.ute.rental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.impl.MaChiTietDonHang;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHangEntity, MaChiTietDonHang> {

    Optional<ChiTietDonHangEntity> findOneByDonHang_MaDonHangAndXeMay_MaXeMay(String maDonHang, String maXeMay);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_MaDonHangAndTrangThaiXoa(String maDonHang,
            String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByTrangThaiXoa(String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHangAndTrangThaiXoa(
            String maKhachHang, String trangThaiDonHang, String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHang(
            String maKhachHang, String trangThaiDonHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_NhanVien_MaNhanVienAndDonHang_TrangThaiDonHangAndTrangThaiXoa(
            String maNhanVien, String trangThaiDonHang, String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_NhanVien_MaNhanVienAndDonHang_TrangThaiDonHang(
            String maNhanVien, String trangThaiDonHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByXeMay_ChuCuaHang_MaChuCuaHangAndDonHang_TrangThaiDonHangAndTrangThaiXoa(
            String maChuCuaHang, String trangThaiDonHang, String trangThaiXoa);

    @Query("SELECT c FROM ChiTietDonHangEntity c WHERE c.tuNgay = :homNay")
    List<ChiTietDonHangEntity> findAllByTuNgay(@Param("homNay") LocalDate homNay);

}
