package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.impl.MaChiTietDonHang;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHangEntity, MaChiTietDonHang> {

    Optional<ChiTietDonHangEntity> findOneByDonHang_MaDonHangAndXeMay_MaXeMay(String maDonHang, String maXeMay);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_MaDonHangAndTrangThaiXoa(String maDonHang,
            String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByTrangThaiXoa(String trangThaiXoa);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHang(String maKhachHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHang(
            String maKhachHang, String trangThaiDonHang);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_NhanVien_MaNhanVien(String maNhanVien);

    List<ChiTietDonHangEntity> findChiTietDonHangsByDonHang_NhanVien_MaNhanVienAndDonHang_TrangThaiDonHang(
            String maNhanVien, String trangThaiDonHang);

}
