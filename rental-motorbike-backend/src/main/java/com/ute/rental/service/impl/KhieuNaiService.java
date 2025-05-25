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
import com.ute.rental.converter.AnhKhieuNaiConverter;
import com.ute.rental.converter.DonHangConverter;
import com.ute.rental.converter.KhieuNaiConverter;
import com.ute.rental.converter.LoaiKhieuNaiConverter;
import com.ute.rental.dto.AnhKhieuNaiDTO;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;
import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.entity.AnhKhieuNaiEntity;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.entity.KhieuNaiEntity;
import com.ute.rental.entity.LoaiKhieuNaiEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.AnhKhieuNaiRepository;
import com.ute.rental.repository.DonHangRepository;
import com.ute.rental.repository.KhieuNaiRepository;
import com.ute.rental.repository.LoaiKhieuNaiRepository;
import com.ute.rental.service.IKhieuNaiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KhieuNaiService implements IKhieuNaiService {

    private final LoaiKhieuNaiRepository loaiKhieuNaiRepository;
    private final DonHangRepository donHangRepository;
    private final KhieuNaiRepository khieuNaiRepository;
    private final KhieuNaiConverter khieuNaiConverter;
    private final LoaiKhieuNaiConverter loaiKhieuNaiConverter;
    private final DonHangConverter donHangConverter;
    private final AnhKhieuNaiRepository anhKhieuNaiRepository;
    private final Cloudinary cloudinary;
    private final AnhKhieuNaiConverter anhKhieuNaiConverter;

    @Override
    public List<KhieuNaiResponseDTO> getKhieuNais() {
        List<KhieuNaiEntity> entities = khieuNaiRepository.findKhieuNaisByTrangThaiXoa("1");
        List<KhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (KhieuNaiEntity khieuNaiEntity : entities) {
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            LoaiKhieuNaiEntity loaiKhieuNaiEntity = khieuNaiEntity.getLoaiKhieuNai();
            LoaiKhieuNaiDTO loaiKhieuNaiDTO = loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);

            DonHangEntity donHangEntity = khieuNaiEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            List<AnhKhieuNaiEntity> anhKhieuNaiEntities = khieuNaiEntity.getAnhKhieuNais();
            List<AnhKhieuNaiDTO> anhKhieuNaiDTOs = anhKhieuNaiConverter.toDTOs(anhKhieuNaiEntities);

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO, anhKhieuNaiDTOs));
        }

        return responseList;
    }

    @Override
    public List<KhieuNaiResponseDTO> getKhieuNaisByMaLoaiKhieuNai(String maLoaiKhieuNai) {
        List<KhieuNaiEntity> entities = khieuNaiRepository
                .findKhieuNaisByTrangThaiXoaAndLoaiKhieuNai_MaLoaiKhieuNai("1", maLoaiKhieuNai);
        List<KhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (KhieuNaiEntity khieuNaiEntity : entities) {
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            LoaiKhieuNaiEntity loaiKhieuNaiEntity = khieuNaiEntity.getLoaiKhieuNai();
            LoaiKhieuNaiDTO loaiKhieuNaiDTO = loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);

            DonHangEntity donHangEntity = khieuNaiEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            List<AnhKhieuNaiEntity> anhKhieuNaiEntities = khieuNaiEntity.getAnhKhieuNais();
            List<AnhKhieuNaiDTO> anhKhieuNaiDTOs = anhKhieuNaiConverter.toDTOs(anhKhieuNaiEntities);

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO, anhKhieuNaiDTOs));
        }

        return responseList;
    }

    @Transactional
    @Override
    public KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO, List<MultipartFile> anhKhieuNaiList) throws IOException {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository
                .findOneByMaLoaiKhieuNai(khieuNaiDTO.getMaLoaiKhieuNai())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là "
                                + khieuNaiDTO.getMaLoaiKhieuNai()));
        DonHangEntity donHangEntity = donHangRepository
                .findOneByMaDonHang(khieuNaiDTO.getMaDonHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là "
                                + khieuNaiDTO.getMaDonHang()));

        KhieuNaiEntity khieuNaiEntity = khieuNaiConverter.toEntity(khieuNaiDTO);
        khieuNaiEntity.setMaKhieuNai(generateMaKhieuNai());
        khieuNaiEntity.setLoaiKhieuNai(loaiKhieuNaiEntity);
        khieuNaiEntity.setDonHang(donHangEntity);
        khieuNaiEntity = khieuNaiRepository.save(khieuNaiEntity);

        // Upload ảnh lên Cloudinary và lưu vào DB
        if (anhKhieuNaiList != null && !anhKhieuNaiList.isEmpty()) {
            for (MultipartFile file : anhKhieuNaiList) {
                Map<String, String> anhKhieuNaiInfo = uploadAnh(file, "khieu-nai");
                AnhKhieuNaiEntity anhKhieuNai = new AnhKhieuNaiEntity();
                anhKhieuNai.setMaAnhKhieuNai(generateMaAnhKhieuNai());
                anhKhieuNai.setTenAnh(anhKhieuNaiInfo.get("publicId"));
                anhKhieuNai.setDuongDan(anhKhieuNaiInfo.get("url"));
                anhKhieuNai.setKhieuNai(khieuNaiEntity);
                anhKhieuNaiRepository.save(anhKhieuNai);
            }
        }

        return khieuNaiConverter.toDTO(khieuNaiEntity);
    }

    @Transactional
    @Override
    public KhieuNaiDTO updateKhieuNai(String maKhieuNai, KhieuNaiDTO updatedKhieuNai,
            List<MultipartFile> anhKhieuNaiList,
            List<String> deletedAnhKhieuNais) throws IOException {
        KhieuNaiEntity khieuNaiEntity = khieuNaiRepository.findOneByMaKhieuNai(maKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là " + maKhieuNai));

        if (updatedKhieuNai.getMaLoaiKhieuNai() != null) {
            LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository
                    .findOneByMaLoaiKhieuNai(updatedKhieuNai.getMaLoaiKhieuNai())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là "
                                    + updatedKhieuNai.getMaLoaiKhieuNai()));
            khieuNaiEntity.setLoaiKhieuNai(loaiKhieuNaiEntity);
        }
        if (updatedKhieuNai.getMaDonHang() != null) {
            DonHangEntity donHangEntity = donHangRepository
                    .findOneByMaDonHang(updatedKhieuNai.getMaDonHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy đơn hàng nào với mã đơn hàng là "
                                    + updatedKhieuNai.getMaDonHang()));
            khieuNaiEntity.setDonHang(donHangEntity);
        }

        if (deletedAnhKhieuNais != null && !deletedAnhKhieuNais.isEmpty()) {
            for (String imageName : deletedAnhKhieuNais) {
                AnhKhieuNaiEntity anhKhieuNai = anhKhieuNaiRepository.findOneByTenAnh(imageName)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh: " + imageName));
                cloudinary.uploader().destroy(anhKhieuNai.getDuongDan(), ObjectUtils.emptyMap());
                khieuNaiEntity.getAnhKhieuNais().remove(anhKhieuNai);
                anhKhieuNaiRepository.delete(anhKhieuNai);
            }
        }

        if (anhKhieuNaiList != null && !anhKhieuNaiList.isEmpty()) {
            for (MultipartFile file : anhKhieuNaiList) {
                Map<String, String> anhKhieuNaiInfo = uploadAnh(file, "khieu-nai");
                AnhKhieuNaiEntity anhKhieuNai = new AnhKhieuNaiEntity();
                anhKhieuNai.setMaAnhKhieuNai(generateMaAnhKhieuNai());
                anhKhieuNai.setTenAnh(anhKhieuNaiInfo.get("publicId"));
                anhKhieuNai.setDuongDan(anhKhieuNaiInfo.get("url"));
                anhKhieuNai.setKhieuNai(khieuNaiEntity);
                anhKhieuNaiRepository.save(anhKhieuNai);
            }
        }

        KhieuNaiEntity khieuNaiUpdated = khieuNaiConverter.toEntity(updatedKhieuNai, khieuNaiEntity);
        return khieuNaiConverter.toDTO(khieuNaiRepository.save(khieuNaiUpdated));
    }

    @Transactional
    @Override
    public void deleteKhieuNai(String maKhieuNai) {
        KhieuNaiEntity khieuNaiEntity = khieuNaiRepository.findOneByMaKhieuNai(maKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là " + maKhieuNai));
        khieuNaiEntity.setTrangThaiXoa("0");
        khieuNaiRepository.save(khieuNaiEntity);
    }

    @Override
    public KhieuNaiResponseDTO getKhieuNaiByMaKhieuNai(String maKhieuNai) {
        KhieuNaiEntity khieuNaiEntity = khieuNaiRepository.findOneByMaKhieuNai(maKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là " + maKhieuNai));
        KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

        LoaiKhieuNaiEntity loaiKhieuNaiEntity = khieuNaiEntity.getLoaiKhieuNai();
        LoaiKhieuNaiDTO loaiKhieuNaiDTO = loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);

        DonHangEntity donHangEntity = khieuNaiEntity.getDonHang();
        DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

        List<AnhKhieuNaiEntity> anhKhieuNaiEntities = khieuNaiEntity.getAnhKhieuNais();
        List<AnhKhieuNaiDTO> anhKhieuNaiDTOs = anhKhieuNaiConverter.toDTOs(anhKhieuNaiEntities);

        return new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO, anhKhieuNaiDTOs);
    }

    private String generateMaKhieuNai() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = khieuNaiRepository.countByMaKhieuNaiStartingWith("KN" + datePart) + 1;

        String stt = String.valueOf(count);

        return "KN" + datePart + stt;
    }

    @Override
    public List<KhieuNaiResponseDTO> getKhieuNaisByMaKhachHang(String maKhachHang) {
        List<KhieuNaiEntity> entities = khieuNaiRepository
                .findByTrangThaiXoaAndDonHang_KhachHang_MaKhachHang("1", maKhachHang);
        List<KhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (KhieuNaiEntity khieuNaiEntity : entities) {
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            LoaiKhieuNaiEntity loaiKhieuNaiEntity = khieuNaiEntity.getLoaiKhieuNai();
            LoaiKhieuNaiDTO loaiKhieuNaiDTO = loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);

            DonHangEntity donHangEntity = khieuNaiEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            List<AnhKhieuNaiEntity> anhKhieuNaiEntities = khieuNaiEntity.getAnhKhieuNais();
            List<AnhKhieuNaiDTO> anhKhieuNaiDTOs = anhKhieuNaiConverter.toDTOs(anhKhieuNaiEntities);

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO, anhKhieuNaiDTOs));
        }

        return responseList;
    }

    @Override
    public List<KhieuNaiResponseDTO> getKhieuNaisByMaChuCuaHang(String maChuCuaHang) {
        List<KhieuNaiEntity> entities = khieuNaiRepository
                .findByTrangThaiXoaAndDonHang_NhanVien_ChuCuaHang_MaChuCuaHang("1", maChuCuaHang);
        List<KhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (KhieuNaiEntity khieuNaiEntity : entities) {
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            LoaiKhieuNaiEntity loaiKhieuNaiEntity = khieuNaiEntity.getLoaiKhieuNai();
            LoaiKhieuNaiDTO loaiKhieuNaiDTO = loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);

            DonHangEntity donHangEntity = khieuNaiEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            List<AnhKhieuNaiEntity> anhKhieuNaiEntities = khieuNaiEntity.getAnhKhieuNais();
            List<AnhKhieuNaiDTO> anhKhieuNaiDTOs = anhKhieuNaiConverter.toDTOs(anhKhieuNaiEntities);

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO, anhKhieuNaiDTOs));
        }

        return responseList;
    }

    @SuppressWarnings({ "null", "unchecked" })
    private Map<String, String> uploadAnh(MultipartFile file, String folderName) throws IOException {
        Map<String, String> fileInfo = new HashMap<>();

        // check valid image
        if (file == null || file.isEmpty()) {
            fileInfo.put("publicId", null);
            fileInfo.put("url", null);
        } else {
            if (!file.getContentType().startsWith("image/")) {
                throw new ResourceNotFormatException("Phải là file ảnh!");
            }
            // upload image
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", folderName));

            // get info from cloudinary
            String publicId = (String) result.get("public_id");
            String url = (String) result.get("url");

            fileInfo.put("publicId", publicId);
            fileInfo.put("url", url);
        }

        return fileInfo;
    }

    private String generateMaAnhKhieuNai() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = anhKhieuNaiRepository.countByMaAnhKhieuNaiStartingWith("AXM" + datePart) + 1;

        String stt = String.valueOf(count);

        return "AXM" + datePart + stt;
    }

}
