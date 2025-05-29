package com.ute.rental.schedule;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.repository.ChiTietDonHangRepository;
import com.ute.rental.service.IXeMayService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XeMayScheduler {

    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final IXeMayService xeMayService;

    @Scheduled(cron = "0 1 0 * * *")
    public void capNhatTrangThaiXeMayTheoNgay() {
        LocalDate homNay = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        List<ChiTietDonHangEntity> danhSachDonHang = chiTietDonHangRepository.findAllByTuNgay(homNay);

        for (ChiTietDonHangEntity chiTiet : danhSachDonHang) {
            String maXeMay = chiTiet.getXeMay().getMaXeMay();
            xeMayService.capNhatTrangThaiXeMay(maXeMay, "Đang cho thuê");
        }
    }

}
