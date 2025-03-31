package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.DonHangConverter;
import com.ute.rental.converter.KhieuNaiConverter;
import com.ute.rental.converter.LoaiKhieuNaiConverter;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.KhieuNaiResponseDTO;
import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.entity.KhieuNaiEntity;
import com.ute.rental.entity.LoaiKhieuNaiEntity;
import com.ute.rental.exception.ResourceNotFoundException;
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

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO));
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

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO));
        }

        return responseList;
    }

    @Transactional
    @Override
    public KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository
                .findOneByMaLoaiKhieuNai(khieuNaiDTO.getMaLoaiKhieuNai())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là "
                                + khieuNaiDTO.getMaLoaiKhieuNai()));
        DonHangEntity donHangEntity = donHangRepository
                .findOneByMaDonHang(khieuNaiDTO.getMaDonHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chi tiết đơn hàng nào với mã chi tiết đơn hàng là "
                                + khieuNaiDTO.getMaLoaiKhieuNai()));

        KhieuNaiEntity khieuNaiEntity = khieuNaiConverter.toEntity(khieuNaiDTO);
        khieuNaiEntity.setMaKhieuNai(generateMaKhieuNai());
        khieuNaiEntity.setLoaiKhieuNai(loaiKhieuNaiEntity);
        khieuNaiEntity.setDonHang(donHangEntity);
        return khieuNaiConverter.toDTO(khieuNaiRepository.save(khieuNaiEntity));
    }

    @Transactional
    @Override
    public KhieuNaiDTO updateKhieuNai(String maKhieuNai, KhieuNaiDTO updatedKhieuNai) {
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
                            "Không tìm thấy chi tiết đơn hàng nào với mã chi tiết đơn hàng là "
                                    + updatedKhieuNai.getMaLoaiKhieuNai()));
            khieuNaiEntity.setDonHang(donHangEntity);
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

        return new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO);
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

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO));
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

            responseList.add(new KhieuNaiResponseDTO(khieuNaiDTO, loaiKhieuNaiDTO, donHangDTO));
        }

        return responseList;
    }

}
