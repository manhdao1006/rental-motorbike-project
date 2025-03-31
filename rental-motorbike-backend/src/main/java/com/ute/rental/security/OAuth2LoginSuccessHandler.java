package com.ute.rental.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.NguoiDungRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTGenerator jwtGenerator;
    private final NguoiDungRepository nguoiDungRepository;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại trong hệ thống!"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = jwtGenerator.generateToken(userDetails);
        String refreshToken = jwtGenerator.generateRefreshToken(userDetails.getUsername());

        response.sendRedirect(
                "http://localhost:3000/login-success?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
