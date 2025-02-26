package com.ute.rental.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.NguoiDungRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy email!"));
        return new User(nguoiDungEntity.getEmail(), nguoiDungEntity.getMatKhau(),
                mapRolesToAuthorities(nguoiDungEntity.getVaiTros()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<VaiTroEntity> vaiTros) {
        return vaiTros.stream().map(vaiTro -> new SimpleGrantedAuthority(vaiTro.getTenVaiTro()))
                .collect(Collectors.toList());
    }

}
