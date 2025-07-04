package com.ute.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.rental.entity.DonHangEntity;

@Repository
public interface DonHangRepository extends JpaRepository<DonHangEntity, String> {

    Optional<DonHangEntity> findOneByMaDonHang(String maDonHang);

    List<DonHangEntity> findDonHangsByKhachHang_MaKhachHangAndTrangThaiXoa(String maKhachHang, String trangThaiXoa);

    List<DonHangEntity> findDonHangsByNhanVien_MaNhanVienAndTrangThaiDonHangAndTrangThaiXoa(String maNhanVien,
            String trangThaiDonHang, String trangThaiXoa);

    List<DonHangEntity> findDonHangsByNhanVien_ChuCuaHang_MaChuCuaHangAndTrangThaiDonHangAndTrangThaiXoa(
            String maNhanVien, String trangThaiDonHang, String trangThaiXoa);

    List<DonHangEntity> findDonHangsByTrangThaiXoa(String trangThaiXoa);

    @Query("SELECT COUNT(dh) FROM DonHangEntity dh WHERE dh.maDonHang LIKE :prefix%")
    int countByMaDonHangStartingWith(@Param("prefix") String prefix);

    @Query("""
                SELECT dh FROM DonHangEntity dh
                WHERE dh.trangThaiThanhToan = 'Chưa thanh toán' AND dh.phuongThucThanhToan = 'Thanh toán qua ngân hàng'
            """)
    List<DonHangEntity> findDonHangChuaThanhToan();

}
