package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.ChuCuaHangConverter;
import com.ute.rental.converter.DonHangConverter;
import com.ute.rental.converter.KhachHangConverter;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.DonHangResponseDTO;
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.ChuCuaHangRepository;
import com.ute.rental.repository.DonHangRepository;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.service.IDonHangService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonHangService implements IDonHangService {

    private final DonHangRepository donHangRepository;
    private final ChuCuaHangRepository chuCuaHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final DonHangConverter donHangConverter;
    private final ChuCuaHangConverter chuCuaHangConverter;
    private final KhachHangConverter khachHangConverter;

    @Override
    public List<DonHangResponseDTO> getDonHangsByMaChuCuaHang(String maChuCuaHang) {
        List<DonHangEntity> entities = donHangRepository
                .findDonHangsByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(maChuCuaHang, "1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            responseList.add(new DonHangResponseDTO(donHangDTO, khachHangDTO, chuCuaHangDTO));
        }

        return responseList;
    }

    @Override
    public List<DonHangResponseDTO> getDonHangsByMaKhachHang(String maKhachHang) {
        List<DonHangEntity> entities = donHangRepository
                .findDonHangsByKhachHang_MaKhachHangAndTrangThaiXoa(maKhachHang, "1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            responseList.add(new DonHangResponseDTO(donHangDTO, khachHangDTO, chuCuaHangDTO));
        }

        return responseList;
    }

    @Override
    public List<DonHangResponseDTO> getDonHangs() {
        List<DonHangEntity> entities = donHangRepository.findDonHangsByTrangThaiXoa("1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            responseList.add(new DonHangResponseDTO(donHangDTO, khachHangDTO, chuCuaHangDTO));
        }

        return responseList;
    }

    @Transactional
    @Override
    public DonHangDTO addDonHang(DonHangDTO donHangDTO) {
        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(donHangDTO.getMaChuCuaHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                + donHangDTO.getMaChuCuaHang()));
        KhachHangEntity khachHangEntity = khachHangRepository.findOneByMaKhachHang(donHangDTO.getMaKhachHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khách hàng nào với mã khách hàng là: "
                                + donHangDTO.getMaKhachHang()));
        DonHangEntity donHangEntity = donHangConverter.toEntity(donHangDTO);
        donHangEntity.setMaDonHang(generateMaDonHang());
        donHangEntity.setChuCuaHang(chuCuaHangEntity);
        donHangEntity.setKhachHang(khachHangEntity);
        donHangEntity = donHangRepository.save(donHangEntity);

        return donHangConverter.toDTO(donHangEntity);
    }

    @Transactional
    @Override
    public DonHangDTO updateDonHang(String maDonHang, DonHangDTO donHangDTO) {
        DonHangEntity donHangEntity = donHangRepository.findOneByMaDonHang(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                + maDonHang));

        if (donHangDTO.getMaChuCuaHang() != null) {
            ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(donHangDTO.getMaChuCuaHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                    + donHangDTO.getMaChuCuaHang()));
            donHangEntity.setChuCuaHang(chuCuaHangEntity);
        }
        if (donHangDTO.getMaKhachHang() != null) {
            KhachHangEntity khachHangEntity = khachHangRepository
                    .findOneByMaKhachHang(donHangDTO.getMaKhachHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy khách hàng nào với mã khách hàng là: "
                                    + donHangDTO.getMaKhachHang()));
            donHangEntity.setKhachHang(khachHangEntity);
        }

        DonHangEntity donHangUpdated = donHangConverter.toEntity(donHangDTO, donHangEntity);
        return donHangConverter.toDTO(donHangRepository.save(donHangUpdated));
    }

    @Transactional
    @Override
    public void deleteDonHang(String maDonHang) {
        DonHangEntity donHangEntity = donHangRepository.findOneByMaDonHang(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                + maDonHang));
        donHangEntity.setTrangThaiXoa("0");
        donHangRepository.save(donHangEntity);
    }

    @Override
    public DonHangResponseDTO getDonHangByMaDonHang(String maDonHang) {
        DonHangEntity donHangEntity = donHangRepository.findOneByMaDonHang(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy sản phẩm nào với mã sản phẩm là: "
                                + maDonHang));
        DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

        ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getChuCuaHang();
        ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

        KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
        KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

        return new DonHangResponseDTO(donHangDTO, khachHangDTO, chuCuaHangDTO);
    }

    private String generateMaDonHang() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = donHangRepository.countByMaDonHangStartingWith("DH" + datePart) + 1;

        String stt = String.valueOf(count);

        return "DH" + datePart + stt;
    }

}
