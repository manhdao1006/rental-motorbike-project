package com.ute.rental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.KhieuNaiConverter;
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.KhieuNaiEntity;
import com.ute.rental.entity.LoaiKhieuNaiEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.ChiTietDonHangRepository;
import com.ute.rental.repository.KhieuNaiRepository;
import com.ute.rental.repository.LoaiKhieuNaiRepository;
import com.ute.rental.service.IKhieuNaiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KhieuNaiService implements IKhieuNaiService {

    private final LoaiKhieuNaiRepository loaiKhieuNaiRepository;
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final KhieuNaiRepository khieuNaiRepository;
    private final KhieuNaiConverter khieuNaiConverter;

    @Override
    public List<KhieuNaiDTO> getKhieuNais() {
        List<KhieuNaiEntity> entities = khieuNaiRepository.findKhieuNaisByTrangThaiXoa("1");
        return entities.stream().map(khieuNaiConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<KhieuNaiDTO> getKhieuNaisByMaLoaiKhieuNai(String maLoaiKhieuNai) {
        List<KhieuNaiEntity> entities = khieuNaiRepository.findKhieuNaisByTrangThaiXoaAndLoaiKhieuNai_MaLoaiKhieuNai(
                "1",
                maLoaiKhieuNai);
        return entities.stream().map(khieuNaiConverter::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public KhieuNaiDTO addKhieuNai(KhieuNaiDTO khieuNaiDTO) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository
                .findOneByMaLoaiKhieuNai(khieuNaiDTO.getMaLoaiKhieuNai())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là "
                                + khieuNaiDTO.getMaLoaiKhieuNai()));
        ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangRepository
                .findOneByMaChiTietDonHang(khieuNaiDTO.getMaChiTietDonHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chi tiết đơn hàng nào với mã chi tiết đơn hàng là "
                                + khieuNaiDTO.getMaLoaiKhieuNai()));

        KhieuNaiEntity khieuNaiEntity = khieuNaiConverter.toEntity(khieuNaiDTO);
        khieuNaiEntity.setLoaiKhieuNai(loaiKhieuNaiEntity);
        khieuNaiEntity.setChiTietDonHang(chiTietDonHangEntity);
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
        if (updatedKhieuNai.getMaChiTietDonHang() != null) {
            ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangRepository
                    .findOneByMaChiTietDonHang(updatedKhieuNai.getMaChiTietDonHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy chi tiết đơn hàng nào với mã chi tiết đơn hàng là "
                                    + updatedKhieuNai.getMaLoaiKhieuNai()));
            khieuNaiEntity.setChiTietDonHang(chiTietDonHangEntity);
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
    public KhieuNaiDTO getKhieuNaiByMaKhieuNai(String maKhieuNai) {
        KhieuNaiEntity khieuNaiEntity = khieuNaiRepository.findOneByMaKhieuNai(maKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là " + maKhieuNai));
        return khieuNaiConverter.toDTO(khieuNaiEntity);
    }

}
