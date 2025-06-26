package com.ute.rental.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ute.rental.entity.CustomOAuth2User;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.repository.NguoiDungRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final NguoiDungRepository nguoiDungRepository;
    private final GoogleUserService googleUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        NguoiDungEntity user = nguoiDungRepository.findByEmail(email).orElseGet(() -> {
            try {
                return googleUserService.createNewUser(email, name, picture);
            } catch (DataIntegrityViolationException e) {
                return nguoiDungRepository.findByEmail(email).orElseThrow(
                        () -> new RuntimeException("Tạo người dùng thất bại và không thể tìm lại người dùng."));
            }
        });

        return new CustomOAuth2User(oAuth2User, user);
    }
}
