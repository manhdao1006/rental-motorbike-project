package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ute.rental.converter.NguoiDungConverter;
import com.ute.rental.converter.VaiTroConverter;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NguoiDungResponseDTO;
import com.ute.rental.dto.VaiTroDTO;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceExistedException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.ChuCuaHangRepository;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.VaiTroRepository;
import com.ute.rental.service.INguoiDungService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NguoiDungService implements INguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChuCuaHangRepository chuCuaHangRepository;
    private final NguoiDungConverter nguoiDungConverter;
    private final VaiTroConverter vaiTroConverter;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void forgotPassword(String email, String password) {
        NguoiDungEntity nguoiDungEntity = (nguoiDungRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy người dùng nào với email là " + email)));

        nguoiDungEntity.setMatKhau(passwordEncoder.encode(password));
        nguoiDungRepository.save(nguoiDungEntity);
    }

    @Transactional
    @Override
    public NguoiDungDTO register(NguoiDungDTO nguoiDungDTO) {
        if (nguoiDungRepository.existsByEmail(nguoiDungDTO.getEmail())) {
            throw new ResourceExistedException("Email đã tồn tại!");
        }

        NguoiDungEntity nguoiDungEntity = nguoiDungConverter.toEntity(nguoiDungDTO);
        nguoiDungEntity.setMaNguoiDung(generateMaNguoiDung());
        nguoiDungEntity.setEmail(nguoiDungDTO.getEmail());
        nguoiDungEntity.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        nguoiDungEntity.setTrangThaiHoatDong("Hoạt động");

        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_KHACHHANG")
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vai trò!"));
        nguoiDungEntity.setVaiTros(Collections.singletonList(vaiTros));

        NguoiDungEntity savedUserEntity = nguoiDungRepository.save(nguoiDungEntity);

        KhachHangEntity khachHangEntity = new KhachHangEntity();
        khachHangEntity.setNguoiDung(savedUserEntity);
        khachHangRepository.save(khachHangEntity);

        return nguoiDungConverter.toDTO(savedUserEntity);
    }

    @Transactional
    @Override
    public NguoiDungDTO registerAdmin(NguoiDungDTO nguoiDungDTO) {
        if (nguoiDungRepository.existsByEmail(nguoiDungDTO.getEmail())) {
            throw new ResourceExistedException("Email đã tồn tại!");
        }

        NguoiDungEntity nguoiDungEntity = nguoiDungConverter.toEntity(nguoiDungDTO);
        nguoiDungEntity.setMaNguoiDung(generateMaNguoiDung());
        nguoiDungEntity.setEmail(nguoiDungDTO.getEmail());
        nguoiDungEntity.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        nguoiDungEntity.setTrangThaiHoatDong("Hoạt động");

        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_QUANTRIVIEN")
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vai trò!"));
        nguoiDungEntity.setVaiTros(Collections.singletonList(vaiTros));

        NguoiDungEntity savedUserEntity = nguoiDungRepository.save(nguoiDungEntity);

        ChuCuaHangEntity chuCuaHangEntity = new ChuCuaHangEntity();
        chuCuaHangEntity.setNguoiDung(savedUserEntity);
        chuCuaHangRepository.save(chuCuaHangEntity);

        return nguoiDungConverter.toDTO(savedUserEntity);
    }

    @Override
    public List<NguoiDungDTO> getNguoiDungsByMaVaiTro(String maVaiTro) {
        List<NguoiDungEntity> entities = nguoiDungRepository.findNguoiDungsByVaiTro(maVaiTro, "1");

        return entities.stream().map(nguoiDungConverter::toDTO).toList();
    }

    @Override
    public NguoiDungResponseDTO getNguoiDungByMaNguoiDung(String maNguoiDung) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng với mã người dùng là " + maNguoiDung));
        NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);
        List<VaiTroEntity> vaiTros = nguoiDungEntity.getVaiTros();
        VaiTroDTO vaiTroDTO = vaiTros.isEmpty() ? null : vaiTroConverter.toDTO(vaiTros.get(0));

        return new NguoiDungResponseDTO(nguoiDungDTO, vaiTroDTO);
    }

    @Override
    public NguoiDungDTO getNguoiDungByEmail(String email) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng với email là " + email));
        return nguoiDungConverter.toDTO(nguoiDungEntity);
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
