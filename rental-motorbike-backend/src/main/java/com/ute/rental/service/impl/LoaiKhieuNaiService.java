package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.LoaiKhieuNaiConverter;
import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.entity.LoaiKhieuNaiEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.LoaiKhieuNaiRepository;
import com.ute.rental.service.ILoaiKhieuNaiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoaiKhieuNaiService implements ILoaiKhieuNaiService {

    private final LoaiKhieuNaiRepository loaiKhieuNaiRepository;
    private final LoaiKhieuNaiConverter loaiKhieuNaiConverter;

    @Override
    public List<LoaiKhieuNaiDTO> getLoaiKhieuNais() {
        List<LoaiKhieuNaiEntity> entities = loaiKhieuNaiRepository.findLoaiKhieuNaisByTrangThaiXoa("1");
        return entities.stream().map(loaiKhieuNaiConverter::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LoaiKhieuNaiDTO addLoaiKhieuNai(LoaiKhieuNaiDTO loaiKhieuNaiDTO) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiConverter.toEntity(loaiKhieuNaiDTO);
        loaiKhieuNaiEntity.setMaLoaiKhieuNai(generateMaLoaiKhieuNai());
        return loaiKhieuNaiConverter.toDTO(loaiKhieuNaiRepository.save(loaiKhieuNaiEntity));
    }

    @Transactional
    @Override
    public LoaiKhieuNaiDTO updateLoaiKhieuNai(String maLoaiKhieuNai, LoaiKhieuNaiDTO updatedLoaiKhieuNai) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository.findOneByMaLoaiKhieuNai(maLoaiKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là " + maLoaiKhieuNai));
        LoaiKhieuNaiEntity loaiKhieuNaiUpdated = loaiKhieuNaiConverter.toEntity(updatedLoaiKhieuNai,
                loaiKhieuNaiEntity);
        return loaiKhieuNaiConverter.toDTO(loaiKhieuNaiRepository.save(loaiKhieuNaiUpdated));
    }

    @Transactional
    @Override
    public void deleteLoaiKhieuNai(String maLoaiKhieuNai) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository.findOneByMaLoaiKhieuNai(maLoaiKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là " + maLoaiKhieuNai));
        loaiKhieuNaiEntity.setTrangThaiXoa("0");
        loaiKhieuNaiRepository.save(loaiKhieuNaiEntity);
    }

    @Override
    public LoaiKhieuNaiDTO getLoaiKhieuNaiByMaLoaiKhieuNai(String maLoaiKhieuNai) {
        LoaiKhieuNaiEntity loaiKhieuNaiEntity = loaiKhieuNaiRepository.findOneByMaLoaiKhieuNai(maLoaiKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy loại khiếu nại nào với mã loại khiếu nại là " + maLoaiKhieuNai));
        return loaiKhieuNaiConverter.toDTO(loaiKhieuNaiEntity);
    }

    private String generateMaLoaiKhieuNai() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = loaiKhieuNaiRepository.countByMaLoaiKhieuNaiStartingWith("LKN" + datePart) + 1;

        String stt = String.valueOf(count);

        return "LKN" + datePart + stt;
    }
}
