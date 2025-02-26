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
import com.ute.rental.converter.ChuCuaHangConverter;
import com.ute.rental.converter.NguoiDungConverter;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.ChuCuaHangResponseDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.PhuongXaEntity;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.ChuCuaHangRepository;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.PhuongXaRepository;
import com.ute.rental.repository.VaiTroRepository;
import com.ute.rental.service.IChuCuaHangService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChuCuaHangService implements IChuCuaHangService {

    private final ChuCuaHangConverter chuCuaHangConverter;
    private final ChuCuaHangRepository chuCuaHangRepository;
    private final NguoiDungConverter nguoiDungConverter;
    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PhuongXaRepository phuongXaRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Override
    public List<ChuCuaHangResponseDTO> getChuCuaHangs() {
        List<ChuCuaHangEntity> entities = chuCuaHangRepository.findChuCuaHangsByTrangThaiXoa("1");
        List<ChuCuaHangResponseDTO> responseList = new ArrayList<>();
        for (ChuCuaHangEntity chuCuaHangEntity : entities) {
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = chuCuaHangEntity.getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            responseList.add(new ChuCuaHangResponseDTO(nguoiDungDTO, chuCuaHangDTO));
        }

        return responseList;
    }

    @Override
    public ChuCuaHangResponseDTO getChuCuaHangByMaChuCuaHang(String maNguoiDung) {
        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không có người bán nào có mã người bán là " + maNguoiDung));
        ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);
        NguoiDungEntity nguoiDungEntity = chuCuaHangEntity.getNguoiDung();
        NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

        return new ChuCuaHangResponseDTO(nguoiDungDTO, chuCuaHangDTO);
    }

    @Transactional
    @Override
    public ChuCuaHangResponseDTO addChuCuaHang(NguoiDungDTO nguoiDungDTO, ChuCuaHangDTO chuCuaHangDTO,
            MultipartFile avatar)
            throws IOException {
        Map<String, String> avatarInfo = uploadAnhDaiDien(avatar);

        NguoiDungEntity nguoiDungEntity = nguoiDungConverter.toEntity(nguoiDungDTO);
        nguoiDungEntity.setMaNguoiDung(generateMaNguoiDung());
        nguoiDungEntity.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
        nguoiDungEntity.setAnhDaiDienId(avatarInfo.get("publicId"));
        nguoiDungEntity.setAnhDaiDien(avatarInfo.get("url"));
        VaiTroEntity vaiTros = vaiTroRepository.findOneByTenVaiTro("ROLE_CHUCUAHANG")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        nguoiDungEntity.setVaiTros(Collections.singletonList(vaiTros));
        nguoiDungEntity = nguoiDungRepository.save(nguoiDungEntity);

        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangConverter.toEntity(chuCuaHangDTO);
        PhuongXaEntity phuongXaEntity = phuongXaRepository.findOneByMaPhuongXa(chuCuaHangDTO.getMaPhuongXa())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không có phường xã nào với mã phường xã là " + chuCuaHangDTO.getMaPhuongXa()));
        chuCuaHangEntity.setPhuongXa(phuongXaEntity);
        chuCuaHangEntity.setNguoiDung(nguoiDungEntity);
        chuCuaHangEntity = chuCuaHangRepository.save(chuCuaHangEntity);

        return new ChuCuaHangResponseDTO(nguoiDungConverter.toDTO(nguoiDungEntity),
                chuCuaHangConverter.toDTO(chuCuaHangEntity));
    }

    @Transactional
    @Override
    public ChuCuaHangResponseDTO updateChuCuaHang(String maNguoiDung, NguoiDungDTO nguoiDungDTO,
            ChuCuaHangDTO chuCuaHangDTO,
            MultipartFile avatar) throws IOException {
        NguoiDungEntity oldNguoiDung = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        ChuCuaHangEntity oldChuCuaHang = chuCuaHangRepository.findOneByMaChuCuaHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người bán nào với mã người bán là " + maNguoiDung));
        NguoiDungEntity newNguoiDung = nguoiDungConverter.toEntity(nguoiDungDTO, oldNguoiDung);
        ChuCuaHangEntity newChuCuaHang = chuCuaHangConverter.toEntity(chuCuaHangDTO, oldChuCuaHang);

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

        if (chuCuaHangDTO.getMaPhuongXa() != null) {
            PhuongXaEntity phuongXaEntity = phuongXaRepository.findOneByMaPhuongXa(chuCuaHangDTO.getMaPhuongXa())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không có phường xã nào với mã phường xã là " + chuCuaHangDTO.getMaPhuongXa()));
            newChuCuaHang.setPhuongXa(phuongXaEntity);
        }

        newNguoiDung = nguoiDungRepository.save(newNguoiDung);
        newChuCuaHang = chuCuaHangRepository.save(newChuCuaHang);

        return new ChuCuaHangResponseDTO(nguoiDungConverter.toDTO(newNguoiDung),
                chuCuaHangConverter.toDTO(newChuCuaHang));
    }

    @Transactional
    @Override
    public void deleteChuCuaHang(String maNguoiDung) {
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng nào với mã người dùng là " + maNguoiDung));
        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(maNguoiDung)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người bán nào với mã người bán là " + maNguoiDung));
        nguoiDungEntity.setTrangThaiXoa("0");
        nguoiDungEntity.setEmail(null);
        chuCuaHangEntity.setTrangThaiXoa("0");
        nguoiDungRepository.save(nguoiDungEntity);
        chuCuaHangRepository.save(chuCuaHangEntity);
    }

    @Transactional
    @Override
    public void banChuCuaHang(String maNguoiDung) {
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
                    ObjectUtils.asMap("folder", "nguoi-ban"));

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
