package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.ChiTietDonHangConverter;
import com.ute.rental.converter.ChuCuaHangConverter;
import com.ute.rental.converter.DonHangConverter;
import com.ute.rental.converter.KhachHangConverter;
import com.ute.rental.converter.NhanVienConverter;
import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.DonHangResponseDTO;
import com.ute.rental.dto.KhachHangDTO;
import com.ute.rental.dto.NhanVienDTO;
import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.entity.KhachHangEntity;
import com.ute.rental.entity.NhanVienEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.DonHangRepository;
import com.ute.rental.repository.KhachHangRepository;
import com.ute.rental.repository.NhanVienRepository;
import com.ute.rental.service.IDonHangService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonHangService implements IDonHangService {

    private final DonHangRepository donHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final DonHangConverter donHangConverter;
    private final ChuCuaHangConverter chuCuaHangConverter;
    private final KhachHangConverter khachHangConverter;
    private final ChiTietDonHangConverter chiTietDonHangConverter;
    private final NhanVienConverter nhanVienConverter;

    @Override
    public List<DonHangResponseDTO> getDonHangsByNhanVien(String maNhanVien, String trangThaiDonHang) {
        List<DonHangEntity> entities = donHangRepository
                .findDonHangsByNhanVien_MaNhanVienAndTrangThaiDonHangAndTrangThaiXoa(maNhanVien, trangThaiDonHang, "1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getNhanVien().getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            NhanVienEntity nhanVienEntity = donHangEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            List<ChiTietDonHangEntity> chiTietDonHangEntities = donHangEntity.getChiTietDonHangs();
            List<ChiTietDonHangDTO> chiTietDonHangDTOs = chiTietDonHangConverter.toDTOs(chiTietDonHangEntities);

            responseList.add(
                    new DonHangResponseDTO(donHangDTO, khachHangDTO, nhanVienDTO, chuCuaHangDTO, chiTietDonHangDTOs));
        }

        return responseList;
    }

    @Override
    public List<DonHangResponseDTO> getDonHangsByKhachHang(String maKhachHang) {
        List<DonHangEntity> entities = donHangRepository
                .findDonHangsByKhachHang_MaKhachHangAndTrangThaiXoa(maKhachHang, "1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getNhanVien().getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            NhanVienEntity nhanVienEntity = donHangEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            List<ChiTietDonHangEntity> chiTietDonHangEntities = donHangEntity.getChiTietDonHangs();
            List<ChiTietDonHangDTO> chiTietDonHangDTOs = chiTietDonHangConverter.toDTOs(chiTietDonHangEntities);

            responseList.add(
                    new DonHangResponseDTO(donHangDTO, khachHangDTO, nhanVienDTO, chuCuaHangDTO, chiTietDonHangDTOs));
        }

        return responseList;
    }

    @Override
    public List<DonHangResponseDTO> getDonHangs() {
        List<DonHangEntity> entities = donHangRepository.findDonHangsByTrangThaiXoa("1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getNhanVien().getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            NhanVienEntity nhanVienEntity = donHangEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            List<ChiTietDonHangEntity> chiTietDonHangEntities = donHangEntity.getChiTietDonHangs();
            List<ChiTietDonHangDTO> chiTietDonHangDTOs = chiTietDonHangConverter.toDTOs(chiTietDonHangEntities);

            responseList.add(
                    new DonHangResponseDTO(donHangDTO, khachHangDTO, nhanVienDTO, chuCuaHangDTO, chiTietDonHangDTOs));
        }

        return responseList;
    }

    @Transactional
    @Override
    public DonHangDTO addDonHang(DonHangDTO donHangDTO) {
        NhanVienEntity nhanVienEntity = null;
        if (donHangDTO.getMaNhanVien() != null) {
            nhanVienEntity = nhanVienRepository.findOneByMaNhanVien(donHangDTO.getMaNhanVien())
                    .orElse(null);
        }
        KhachHangEntity khachHangEntity = khachHangRepository.findOneByMaKhachHang(donHangDTO.getMaKhachHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khách hàng nào với mã khách hàng là: "
                                + donHangDTO.getMaKhachHang()));
        DonHangEntity donHangEntity = donHangConverter.toEntity(donHangDTO);
        donHangEntity.setMaDonHang(generateMaDonHang());
        donHangEntity.setNhanVien(nhanVienEntity);
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

        if (donHangDTO.getMaNhanVien() != null) {
            NhanVienEntity nhanVienEntity = nhanVienRepository.findOneByMaNhanVien(donHangDTO.getMaNhanVien())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy nhân viên nào với mã nhân viên là: "
                                    + donHangDTO.getMaNhanVien()));
            donHangEntity.setNhanVien(nhanVienEntity);
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
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                + maDonHang));
        DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

        NhanVienEntity nhanVienEntity = donHangEntity.getNhanVien();
        NhanVienDTO nhanVienDTO = (nhanVienEntity != null) ? nhanVienConverter.toDTO(nhanVienEntity) : null;

        ChuCuaHangDTO chuCuaHangDTO = null;
        if (nhanVienEntity != null && nhanVienEntity.getChuCuaHang() != null) {
            chuCuaHangDTO = chuCuaHangConverter.toDTO(nhanVienEntity.getChuCuaHang());
        }

        KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
        KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

        List<ChiTietDonHangEntity> chiTietDonHangEntities = donHangEntity.getChiTietDonHangs();
        List<ChiTietDonHangDTO> chiTietDonHangDTOs = chiTietDonHangConverter.toDTOs(chiTietDonHangEntities);

        return new DonHangResponseDTO(donHangDTO, khachHangDTO, nhanVienDTO, chuCuaHangDTO, chiTietDonHangDTOs);
    }

    private String generateMaDonHang() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = donHangRepository.countByMaDonHangStartingWith("DH" + datePart) + 1;

        String stt = String.valueOf(count);

        return "DH" + datePart + stt;
    }

    @Override
    public List<DonHangResponseDTO> getDonHangsByChuCuaHang(String maChuCuaHang, String trangThaiDonHang) {
        List<DonHangEntity> entities = donHangRepository
                .findDonHangsByNhanVien_ChuCuaHang_MaChuCuaHangAndTrangThaiDonHangAndTrangThaiXoa(maChuCuaHang,
                        trangThaiDonHang, "1");
        List<DonHangResponseDTO> responseList = new ArrayList<>();
        for (DonHangEntity donHangEntity : entities) {
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            ChuCuaHangEntity chuCuaHangEntity = donHangEntity.getNhanVien().getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            KhachHangEntity khachHangEntity = donHangEntity.getKhachHang();
            KhachHangDTO khachHangDTO = khachHangConverter.toDTO(khachHangEntity);

            NhanVienEntity nhanVienEntity = donHangEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            List<ChiTietDonHangEntity> chiTietDonHangEntities = donHangEntity.getChiTietDonHangs();
            List<ChiTietDonHangDTO> chiTietDonHangDTOs = chiTietDonHangConverter.toDTOs(chiTietDonHangEntities);

            responseList.add(
                    new DonHangResponseDTO(donHangDTO, khachHangDTO, nhanVienDTO, chuCuaHangDTO, chiTietDonHangDTOs));
        }

        return responseList;
    }

}
