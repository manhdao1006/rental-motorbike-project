package com.ute.rental.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.repository.DonHangRepository;
import com.ute.rental.service.IDonHangService;
import com.ute.rental.service.IXeMayService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DonHangScheduler {

    private final DonHangRepository donHangRepository;
    private final IDonHangService donHangService;
    private final IXeMayService xeMayService;

    @Scheduled(cron = "0 1 0 * * *")
    public void huyDonHangSau1NgayDat() {
        LocalDate homNay = LocalDate.now();
        List<DonHangEntity> danhSach = donHangRepository.findDonHangChuaThanhToan();

        for (DonHangEntity donHang : danhSach) {
            LocalDate ngayTao = donHang.getNgayTao().toLocalDate();
            if (ngayTao.plusDays(1).isEqual(homNay)) {
                String maDonHang = donHang.getMaDonHang();

                donHangService.capNhatTrangThaiVaLyDoHuy(maDonHang, "Đã hủy", "Không đặt cọc sau 1 ngày");

                for (ChiTietDonHangEntity chiTiet : donHang.getChiTietDonHangs()) {
                    String maXeMay = chiTiet.getXeMay().getMaXeMay();
                    xeMayService.capNhatTrangThaiXeMay(maXeMay, "Có sẵn");
                }
            }
        }
    }

}
