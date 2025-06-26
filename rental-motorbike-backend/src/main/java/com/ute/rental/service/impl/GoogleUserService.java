package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.VaiTroRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleUserService {

    private final NguoiDungRepository nguoiDungRepository;
    private final KhachHangRepository khachHangRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public NguoiDungEntity createNewUser(String email, String name, String picture) {
        NguoiDungEntity newUser = new NguoiDungEntity();
        newUser.setMaNguoiDung(generateMaNguoiDung());
        newUser.setEmail(email);
        newUser.setMatKhau(passwordEncoder.encode("123456"));
        newUser.setHoVaTen(name);
        newUser.setAnhDaiDien(picture);
        newUser.setNgayDangKy(LocalDateTime.now());
        newUser.setTrangThaiHoatDong("Hoạt động");
        newUser.setGioiTinh("Nam");

        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_KHACHHANG")
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vai trò!"));
        newUser.setVaiTros(Collections.singletonList(vaiTros));

        NguoiDungEntity savedUser = nguoiDungRepository.save(newUser);

        KhachHangEntity khachHang = new KhachHangEntity();
        khachHang.setNguoiDung(savedUser);
        khachHangRepository.save(khachHang);

        return savedUser;
    }

    private String generateMaNguoiDung() {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int count = nguoiDungRepository.countByMaNguoiDungStartingWith("ND" + datePart) + 1;
        return "ND" + datePart + count;
    }

}
