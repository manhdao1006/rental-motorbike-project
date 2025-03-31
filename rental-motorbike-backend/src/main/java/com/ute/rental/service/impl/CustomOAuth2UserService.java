package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ute.rental.entity.CustomOAuth2User;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        NguoiDungEntity user = nguoiDungRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(email, name, picture));

        return new CustomOAuth2User(oAuth2User, user);
    }

    private NguoiDungEntity createNewUser(String email, String name, String picture) {
        NguoiDungEntity newUser = new NguoiDungEntity();
        newUser.setMaNguoiDung(generateMaNguoiDung());
        newUser.setEmail(email);
        newUser.setMatKhau(passwordEncoder.encode("User@123")); // Mật khẩu mặc định
        newUser.setHoVaTen(name);
        newUser.setAnhDaiDien(picture);
        newUser.setNgayDangKy(LocalDateTime.now());
        newUser.setTrangThaiHoatDong("Hoạt động");
        newUser.setGioiTinh("Nam");

        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_KHACHHANG")
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vai trò!"));
        newUser.setVaiTros(Collections.singletonList(vaiTros));

        return nguoiDungRepository.save(newUser);
    }

    private String generateMaNguoiDung() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = nguoiDungRepository.countByMaNguoiDungStartingWith("ND" + datePart) + 1;

        String stt = String.valueOf(count);

        return "ND" + datePart + stt;
    }

}
