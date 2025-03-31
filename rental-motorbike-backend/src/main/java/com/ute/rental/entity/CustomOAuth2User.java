package com.ute.rental.entity;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User {

    private final NguoiDungEntity nguoiDungEntity;

    public CustomOAuth2User(OAuth2User oAuth2User, NguoiDungEntity nguoiDungEntity) {
        super(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "email");
        this.nguoiDungEntity = nguoiDungEntity;
    }

    public NguoiDungEntity getNguoiDungEntity() {
        return nguoiDungEntity;
    }

}
