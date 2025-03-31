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
import com.ute.rental.converter.KhachHangConverter;
import com.ute.rental.converter.NguoiDungConverter;
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.dto.KhachHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.VaiTroRepository;
import com.ute.rental.service.IKhachHangService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KhachHangService implements IKhachHangService {

    private final KhachHangConverter khachHangConverter;
    private final KhachHangRepository khachHangRepository;
    private final NguoiDungConverter nguoiDungConverter;
    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Override
    public List<KhachHangResponseDTO> getKhachHangs() {
        List<KhachHangEntity> entities = khachHangRepository.findKhachHangsByTrangThaiXoa("1");
        List<KhachHangResponseDTO> responseList = new ArrayList<>();
        for (KhachHangEntity khachHangEntity : entities) {
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            NguoiDungEntity nguoiDungEntity = khachHangEntity.getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            responseList.add(new KhachHangResponseDTO(nguoiDungDTO, khachHangDTO));
        }

        return responseList;
    }

    @Override
    public KhachHangResponseDTO getKhachHangByMaKhachHang(String maNguoiDung) {
        KhachHangEntity khachHangEntity = khachHangRepository.findOneByMaKhachHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không có khách hàng nào có mã khách hàng là " + maNguoiDung));
        KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);
        NguoiDungEntity nguoiDungEntity = khachHangEntity.getNguoiDung();
        NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

        return new KhachHangResponseDTO(nguoiDungDTO, khachHangDTO);
    }

    @Transactional
    @Override
    public KhachHangResponseDTO addKhachHang(NguoiDungDTO nguoiDungDTO, KhachHangDTO khachHangDTO, MultipartFile avatar)
            throws IOException {
        Map<String, String> avatarInfo = uploadAnhDaiDien(avatar);

        NguoiDungEntity nguoiDungEntity = nguoiDungConverter.toEntity(nguoiDungDTO);
        nguoiDungEntity.setMaNguoiDung(generateMaNguoiDung());
        nguoiDungEntity.setTrangThaiHoatDong("Hoạt động");
        nguoiDungEntity.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        nguoiDungEntity.setAnhDaiDienId(avatarInfo.get("publicId"));
        nguoiDungEntity.setAnhDaiDien(avatarInfo.get("url"));
        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_KHACHHANG")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        nguoiDungEntity.setVaiTros(Collections.singletonList(vaiTros));
        nguoiDungEntity = nguoiDungRepository.save(nguoiDungEntity);

        KhachHangEntity khachHangEntity = khachHangConverter.toEntity(khachHangDTO);
        khachHangEntity.setNguoiDung(nguoiDungEntity);
        khachHangEntity = khachHangRepository.save(khachHangEntity);

        return new KhachHangResponseDTO(nguoiDungConverter.toDTO(nguoiDungEntity),
                khachHangConverter.toDTO(khachHangEntity));
    }

    @Transactional
    @Override
    public KhachHangResponseDTO updateKhachHang(String maNguoiDung, NguoiDungDTO nguoiDungDTO,
            KhachHangDTO khachHangDTO,
            MultipartFile avatar) throws IOException {
        NguoiDungEntity oldNguoiDung = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        KhachHangEntity oldKhachHang = khachHangRepository.findOneByMaKhachHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khách hàng nào với mã khách hàng là " + maNguoiDung));
        NguoiDungEntity newNguoiDung = nguoiDungConverter.toEntity(nguoiDungDTO, oldNguoiDung);
        KhachHangEntity newKhachHang = khachHangConverter.toEntity(khachHangDTO, oldKhachHang);

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
        newKhachHang = khachHangRepository.save(newKhachHang);

        return new KhachHangResponseDTO(nguoiDungConverter.toDTO(newNguoiDung), khachHangConverter.toDTO(newKhachHang));
    }

    @Transactional
    @Override
    public void deleteKhachHang(String maNguoiDung) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        KhachHangEntity khachHangEntity = khachHangRepository.findOneByMaKhachHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khách hàng nào với mã khách hàng là " + maNguoiDung));
        nguoiDungEntity.setTrangThaiXoa("0");
        nguoiDungEntity.setEmail(null);
        khachHangEntity.setTrangThaiXoa("0");
        nguoiDungRepository.save(nguoiDungEntity);
        khachHangRepository.save(khachHangEntity);
    }

    @Transactional
    @Override
    public void banKhachHang(String maNguoiDung) {
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
                    ObjectUtils.asMap("folder", "khach-hang"));

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
