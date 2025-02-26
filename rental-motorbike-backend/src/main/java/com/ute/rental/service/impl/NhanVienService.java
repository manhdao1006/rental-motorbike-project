package com.ute.rental.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ute.rental.converter.NguoiDungConverter;
import com.ute.rental.converter.NhanVienConverter;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NhanVienDTO;
import com.ute.rental.dto.NhanVienResponseDTO;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.NhanVienEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.NhanVienRepository;
import com.ute.rental.repository.VaiTroRepository;
import com.ute.rental.service.INhanVienService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NhanVienService implements INhanVienService {

    private final NhanVienConverter nhanVienConverter;
    private final NhanVienRepository nhanVienRepository;
    private final NguoiDungConverter nguoiDungConverter;
    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Override
    public List<NhanVienResponseDTO> getNhanViens() {

        List<NhanVienEntity> entities = nhanVienRepository.findNhanViensByTrangThaiXoa("1");
        List<NhanVienResponseDTO> responseList = new ArrayList<>();
        for (NhanVienEntity nhanVienEntity : entities) {
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            NguoiDungEntity nguoiDungEntity = nhanVienEntity.getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            responseList.add(new NhanVienResponseDTO(nguoiDungDTO, nhanVienDTO));
        }

        return responseList;
    }

    @Override
    public NhanVienResponseDTO getNhanVienByMaNhanVien(String maNguoiDung) {
        NhanVienEntity nhanVienEntity = nhanVienRepository.findOneByMaNhanVien(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không có nhân viên nào có mã nhân viên là " + maNguoiDung));
        NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);
        NguoiDungEntity nguoiDungEntity = nhanVienEntity.getNguoiDung();
        NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

        return new NhanVienResponseDTO(nguoiDungDTO, nhanVienDTO);
    }

    @Transactional
    @Override
    public NhanVienResponseDTO addNhanVien(NguoiDungDTO nguoiDungDTO, NhanVienDTO nhanVienDTO, MultipartFile avatar)
            throws IOException {
        Map<String, String> avatarInfo = uploadAnhDaiDien(avatar);

        NguoiDungEntity nguoiDungEntity = nguoiDungConverter.toEntity(nguoiDungDTO);
        nguoiDungEntity.setMaNguoiDung(generateMaNguoiDung());
        nguoiDungEntity.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        nguoiDungEntity.setAnhDaiDienId(avatarInfo.get("publicId"));
        nguoiDungEntity.setAnhDaiDien(avatarInfo.get("url"));
        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_NHANVIEN")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        nguoiDungEntity.setVaiTros(Collections.singletonList(vaiTros));
        nguoiDungEntity = nguoiDungRepository.save(nguoiDungEntity);

        NhanVienEntity nhanVienEntity = nhanVienConverter.toEntity(nhanVienDTO);
        nhanVienEntity.setNguoiDung(nguoiDungEntity);
        nhanVienEntity = nhanVienRepository.save(nhanVienEntity);

        return new NhanVienResponseDTO(nguoiDungConverter.toDTO(nguoiDungEntity),
                nhanVienConverter.toDTO(nhanVienEntity));
    }

    @Transactional
    @Override
    public NhanVienResponseDTO updateNhanVien(String maNguoiDung, NguoiDungDTO nguoiDungDTO, NhanVienDTO nhanVienDTO,
            MultipartFile avatar) throws IOException {
        NguoiDungEntity oldNguoiDung = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        NhanVienEntity oldNhanVien = nhanVienRepository.findOneByMaNhanVien(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy nhân viên nào với mã nhân viên là " + maNguoiDung));
        NguoiDungEntity newNguoiDung = nguoiDungConverter.toEntity(nguoiDungDTO, oldNguoiDung);
        NhanVienEntity newNhanVien = nhanVienConverter.toEntity(nhanVienDTO, oldNhanVien);

        if (avatar != null && !avatar.isEmpty()) {
            if (oldNguoiDung.getMaNguoiDung() != null) {
                if (oldNguoiDung.getAnhDaiDienId() != null && !oldNguoiDung.getAnhDaiDienId().isEmpty()) {
                    cloudinary.uploader().destroy(oldNguoiDung.getAnhDaiDienId(), ObjectUtils.emptyMap());
                }
            }

            Map<String, String> avatarInfo = uploadAnhDaiDien(avatar);
            newNguoiDung.setAnhDaiDienId(avatarInfo.get("publicId"));
            newNguoiDung.setAnhDaiDien(avatarInfo.get("url"));
        } else {
            newNguoiDung.setAnhDaiDienId(oldNguoiDung.getAnhDaiDienId());
            newNguoiDung.setAnhDaiDien(oldNguoiDung.getAnhDaiDien());
        }

        if (nguoiDungDTO.getMatKhau() != null && !nguoiDungDTO.getMatKhau().isEmpty()) {
            newNguoiDung.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        }

        newNguoiDung = nguoiDungRepository.save(newNguoiDung);
        newNhanVien = nhanVienRepository.save(newNhanVien);

        return new NhanVienResponseDTO(nguoiDungConverter.toDTO(newNguoiDung), nhanVienConverter.toDTO(newNhanVien));
    }

    @Transactional
    @Override
    public void deleteNhanVien(String maNguoiDung) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        NhanVienEntity nhanVienEntity = nhanVienRepository.findOneByMaNhanVien(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy nhân viên nào với mã nhân viên là " + maNguoiDung));
        nguoiDungEntity.setTrangThaiXoa("0");
        nguoiDungEntity.setEmail(null);
        nhanVienEntity.setTrangThaiXoa("0");
        nguoiDungRepository.save(nguoiDungEntity);
        nhanVienRepository.save(nhanVienEntity);
    }

    @Transactional
    @Override
    public void banNhanVien(String maNguoiDung) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        nguoiDungEntity.setTrangThaiHoatDong("Không hoạt động");
        nguoiDungRepository.save(nguoiDungEntity);
    }

    @SuppressWarnings({ "null", "unchecked" })
    private Map<String, String> uploadAnhDaiDien(MultipartFile avatar) throws IOException {
        Map<String, String> avatarInfo = new HashMap<>();

        // check valid image
        if (avatar == null || avatar.isEmpty()) {
            avatarInfo.put("publicId", null);
            avatarInfo.put("url", null);
        } else {
            if (!avatar.getContentType().startsWith("image/")) {
                throw new ResourceNotFormatException("Phải là file ảnh!");
            }
            // upload image
            Map<String, Object> result = cloudinary.uploader().upload(avatar.getBytes(),
                    ObjectUtils.asMap("folder", "nhan-vien"));

            // get info from cloudinary
            String publicId = (String) result.get("public_id");
            String url = (String) result.get("url");

            avatarInfo.put("publicId", publicId);
            avatarInfo.put("url", url);
        }

        return avatarInfo;
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
