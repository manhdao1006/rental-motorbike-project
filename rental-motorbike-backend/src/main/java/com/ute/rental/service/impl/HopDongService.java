package com.ute.rental.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ute.rental.converter.AnhHopDongConverter;
import com.ute.rental.converter.ChuCuaHangConverter;
import com.ute.rental.converter.HopDongConverter;
import com.ute.rental.converter.KhachHangConverter;
import com.ute.rental.dto.AnhHopDongDTO;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.HopDongDTO;
import com.ute.rental.dto.HopDongResponseDTO;
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.entity.AnhHopDongEntity;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.HopDongEntity;
import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.AnhHopDongRepository;
import com.ute.rental.repository.ChuCuaHangRepository;
import com.ute.rental.repository.HopDongRepository;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.service.IHopDongService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HopDongService implements IHopDongService {

    private final HopDongRepository hopDongRepository;
    private final ChuCuaHangRepository chuCuaHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final AnhHopDongRepository anhHopDongRepository;
    private final HopDongConverter hopDongConverter;
    private final ChuCuaHangConverter chuCuaHangConverter;
    private final KhachHangConverter khachHangConverter;
    private final AnhHopDongConverter anhHopDongConverter;
    private final Cloudinary cloudinary;

    @Override
    public List<HopDongResponseDTO> getHopDongsByMaChuCuaHang(String maChuCuaHang) {
        List<HopDongEntity> entities = hopDongRepository
                .findHopDongsByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(maChuCuaHang, "1");
        List<HopDongResponseDTO> responseList = new ArrayList<>();
        for (HopDongEntity hopDongEntity : entities) {
            HopDongDTO hopDongDTO = hopDongConverter.toDTO(hopDongEntity);

            ChuCuaHangEntity chuCuaHangEntity = hopDongEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = hopDongEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            List<AnhHopDongEntity> anhHopDongEntities = hopDongEntity.getAnhHopDongs();
            List<AnhHopDongDTO> anhHopDongDTOs = anhHopDongConverter.toDTOs(anhHopDongEntities);

            responseList.add(new HopDongResponseDTO(hopDongDTO, khachHangDTO, chuCuaHangDTO, anhHopDongDTOs));
        }

        return responseList;
    }

    @Override
    public List<HopDongResponseDTO> getHopDongsByMaKhachHang(String maKhachHang) {
        List<HopDongEntity> entities = hopDongRepository
                .findHopDongsByKhachHang_MaKhachHangAndTrangThaiXoa(maKhachHang, "1");
        List<HopDongResponseDTO> responseList = new ArrayList<>();
        for (HopDongEntity hopDongEntity : entities) {
            HopDongDTO hopDongDTO = hopDongConverter.toDTO(hopDongEntity);

            ChuCuaHangEntity chuCuaHangEntity = hopDongEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = hopDongEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            List<AnhHopDongEntity> anhHopDongEntities = hopDongEntity.getAnhHopDongs();
            List<AnhHopDongDTO> anhHopDongDTOs = anhHopDongConverter.toDTOs(anhHopDongEntities);

            responseList.add(new HopDongResponseDTO(hopDongDTO, khachHangDTO, chuCuaHangDTO, anhHopDongDTOs));
        }

        return responseList;
    }

    @Override
    public List<HopDongResponseDTO> getHopDongs() {
        List<HopDongEntity> entities = hopDongRepository.findHopDongsByTrangThaiXoa("1");
        List<HopDongResponseDTO> responseList = new ArrayList<>();
        for (HopDongEntity hopDongEntity : entities) {
            HopDongDTO hopDongDTO = hopDongConverter.toDTO(hopDongEntity);

            ChuCuaHangEntity chuCuaHangEntity = hopDongEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = hopDongEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            List<AnhHopDongEntity> anhHopDongEntities = hopDongEntity.getAnhHopDongs();
            List<AnhHopDongDTO> anhHopDongDTOs = anhHopDongConverter.toDTOs(anhHopDongEntities);

            responseList.add(new HopDongResponseDTO(hopDongDTO, khachHangDTO, chuCuaHangDTO, anhHopDongDTOs));
        }

        return responseList;
    }

    @Transactional
    @Override
    public HopDongDTO addHopDong(HopDongDTO hopDongDTO, List<MultipartFile> anhHopDongList) throws IOException {
        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(hopDongDTO.getMaChuCuaHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                + hopDongDTO.getMaChuCuaHang()));
        KhachHangEntity khachHangEntity = khachHangRepository.findOneByMaKhachHang(hopDongDTO.getMaKhachHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khách hàng nào với mã khách hàng là: "
                                + hopDongDTO.getMaKhachHang()));
        HopDongEntity hopDongEntity = hopDongConverter.toEntity(hopDongDTO);
        hopDongEntity.setMaHopDong(generateMaHopDong());
        hopDongEntity.setChuCuaHang(chuCuaHangEntity);
        hopDongEntity.setKhachHang(khachHangEntity);
        hopDongEntity = hopDongRepository.save(hopDongEntity);

        // Upload ảnh lên Cloudinary và lưu vào DB
        if (anhHopDongList != null && !anhHopDongList.isEmpty()) {
            for (MultipartFile file : anhHopDongList) {
                Map<String, String> anhHopDongInfo = uploadAnhHopDong(file);
                AnhHopDongEntity anhHopDong = new AnhHopDongEntity();
                anhHopDong.setMaAnhHopDong(generateMaAnhHopDong());
                anhHopDong.setDuongDan(anhHopDongInfo.get("publicId"));
                anhHopDong.setTenAnh(anhHopDongInfo.get("url"));
                anhHopDong.setHopDong(hopDongEntity);
                anhHopDongRepository.save(anhHopDong);
            }
        }
        return hopDongConverter.toDTO(hopDongEntity);
    }

    @Transactional
    @Override
    public HopDongDTO updateHopDong(String maHopDong, HopDongDTO hopDongDTO, List<MultipartFile> anhHopDongList,
            List<String> deletedHopDongs)
            throws IOException {
        HopDongEntity hopDongEntity = hopDongRepository.findOneByMaHopDong(maHopDong)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy hợp đồng nào với mã hợp đồng là: "
                                + maHopDong));

        if (hopDongDTO.getMaChuCuaHang() != null) {
            ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(hopDongDTO.getMaChuCuaHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                    + hopDongDTO.getMaChuCuaHang()));
            hopDongEntity.setChuCuaHang(chuCuaHangEntity);
        }
        if (hopDongDTO.getMaKhachHang() != null) {
            KhachHangEntity khachHangEntity = khachHangRepository
                    .findOneByMaKhachHang(hopDongDTO.getMaKhachHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy khách hàng nào với mã khách hàng là: "
                                    + hopDongDTO.getMaKhachHang()));
            hopDongEntity.setKhachHang(khachHangEntity);
        }

        if (deletedHopDongs != null && !deletedHopDongs.isEmpty()) {
            for (String imageName : deletedHopDongs) {
                AnhHopDongEntity anhHopDong = anhHopDongRepository.findOneByTenAnh(imageName)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh: " + imageName));
                cloudinary.uploader().destroy(anhHopDong.getDuongDan(), ObjectUtils.emptyMap());
                hopDongEntity.getAnhHopDongs().remove(anhHopDong);
                anhHopDongRepository.delete(anhHopDong);
            }
        }

        if (anhHopDongList != null && !anhHopDongList.isEmpty()) {
            for (MultipartFile file : anhHopDongList) {
                Map<String, String> anhHopDongInfo = uploadAnhHopDong(file);
                AnhHopDongEntity anhHopDong = new AnhHopDongEntity();
                anhHopDong.setDuongDan(anhHopDongInfo.get("publicId"));
                anhHopDong.setTenAnh(anhHopDongInfo.get("url"));
                anhHopDong.setHopDong(hopDongEntity);
                anhHopDongRepository.save(anhHopDong);
            }
        }

        HopDongEntity hopDongUpdated = hopDongConverter.toEntity(hopDongDTO, hopDongEntity);
        return hopDongConverter.toDTO(hopDongRepository.save(hopDongUpdated));
    }

    @Transactional
    @Override
    public void deleteHopDong(String maHopDong) {
        HopDongEntity hopDongEntity = hopDongRepository.findOneByMaHopDong(maHopDong)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy hợp đồng nào với mã hợp đồng là: "
                                + maHopDong));
        hopDongEntity.setTrangThaiXoa("0");
        hopDongRepository.save(hopDongEntity);
    }

    @SuppressWarnings({ "null", "unchecked" })
    private Map<String, String> uploadAnhHopDong(MultipartFile anhHopDong) throws IOException {
        Map<String, String> anhHopDongInfo = new HashMap<>();

        // check valid image
        if (anhHopDong == null || anhHopDong.isEmpty()) {
            anhHopDongInfo.put("publicId", null);
            anhHopDongInfo.put("url", null);
        } else {
            if (!anhHopDong.getContentType().startsWith("image/")) {
                throw new ResourceNotFormatException("Phải là file ảnh!");
            }
            // upload image
            Map<String, Object> result = cloudinary.uploader().upload(anhHopDong.getBytes(),
                    ObjectUtils.asMap("folder", "hop-dong"));

            // get info from cloudinary
            String publicId = (String) result.get("public_id");
            String url = (String) result.get("url");

            anhHopDongInfo.put("publicId", publicId);
            anhHopDongInfo.put("url", url);
        }

        return anhHopDongInfo;
    }

    @Override
    public HopDongResponseDTO getHopDongByMaHopDong(String maHopDong) {
        HopDongEntity hopDongEntity = hopDongRepository.findOneByMaHopDong(maHopDong)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy sản phẩm nào với mã sản phẩm là: "
                                + maHopDong));
        HopDongDTO hopDongDTO = hopDongConverter.toDTO(hopDongEntity);

        ChuCuaHangEntity chuCuaHangEntity = hopDongEntity.getChuCuaHang();
        ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

        KhachHangEntity khachHangEntity = hopDongEntity.getKhachHang();
        KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

        List<AnhHopDongEntity> anhHopDongEntities = hopDongEntity.getAnhHopDongs();
        List<AnhHopDongDTO> anhHopDongDTOs = anhHopDongConverter.toDTOs(anhHopDongEntities);

        return new HopDongResponseDTO(hopDongDTO, khachHangDTO, chuCuaHangDTO, anhHopDongDTOs);
    }

    private String generateMaHopDong() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = hopDongRepository.countByMaHopDongStartingWith("HD" + datePart) + 1;

        String stt = String.valueOf(count);

        return "HD" + datePart + stt;
    }

    private String generateMaAnhHopDong() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = anhHopDongRepository.countByMaAnhHopDongStartingWith("AHD" + datePart) + 1;

        String stt = String.valueOf(count);

        return "AHD" + datePart + stt;
    }

}
