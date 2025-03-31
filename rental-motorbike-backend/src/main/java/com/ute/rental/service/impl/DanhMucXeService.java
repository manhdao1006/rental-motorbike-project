package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.DanhMucXeConverter;
import com.ute.rental.dto.DanhMucXeDTO;
import com.ute.rental.entity.DanhMucXeEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.DanhMucXeRepository;
import com.ute.rental.service.IDanhMucXeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanhMucXeService implements IDanhMucXeService {

    private final DanhMucXeRepository danhMucXeRepository;
    private final DanhMucXeConverter danhMucXeConverter;

    @Override
    public List<DanhMucXeDTO> getDanhMucXes() {
        List<DanhMucXeEntity> entities = danhMucXeRepository.findDanhMucsByTrangThaiXoa("1");
        return entities.stream().map(danhMucXeConverter::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DanhMucXeDTO addDanhMucXe(DanhMucXeDTO danhMucXeDTO) {
        DanhMucXeEntity danhMucXeEntity = danhMucXeConverter.toEntity(danhMucXeDTO);
        danhMucXeEntity.setMaDanhMucXe(generateMaDanhMucXe());
        return danhMucXeConverter.toDTO(danhMucXeRepository.save(danhMucXeEntity));
    }

    @Transactional
    @Override
    public DanhMucXeDTO updateDanhMucXe(String maDanhMucXe, DanhMucXeDTO updatedDanhMucXe) {
        DanhMucXeEntity danhMucXeEntity = danhMucXeRepository.findOneByMaDanhMucXe(maDanhMucXe)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy danh mục xe nào với mã danh mục xe là " + maDanhMucXe));
        DanhMucXeEntity danhMucUpdated = danhMucXeConverter.toEntity(updatedDanhMucXe, danhMucXeEntity);
        return danhMucXeConverter.toDTO(danhMucXeRepository.save(danhMucUpdated));
    }

    @Transactional
    @Override
    public void deleteDanhMucXe(String maDanhMucXe) {
        DanhMucXeEntity danhMucXeEntity = danhMucXeRepository.findOneByMaDanhMucXe(maDanhMucXe)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy danh mục xe nào với mã danh mục xe là " + maDanhMucXe));
        danhMucXeEntity.setTrangThaiXoa("0");
        danhMucXeRepository.save(danhMucXeEntity);
    }

    @Override
    public DanhMucXeDTO getDanhMucXeByMaDanhMucXe(String maDanhMucXe) {
        DanhMucXeEntity danhMucXeEntity = danhMucXeRepository.findOneByMaDanhMucXe(maDanhMucXe)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy danh mục xe nào với mã danh mục xe là " + maDanhMucXe));
        return danhMucXeConverter.toDTO(danhMucXeEntity);
    }

    @Override
    public List<DanhMucXeDTO> getNavigations() {
        List<DanhMucXeEntity> entities = danhMucXeRepository.findFirst6ByTrangThaiXoaOrderByMaDanhMucXeAsc("1");
        return entities.stream().map(danhMucXeConverter::toDTO).collect(Collectors.toList());
    }

    private String generateMaDanhMucXe() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = danhMucXeRepository.countByMaDanhMucXeStartingWith("DMX" + datePart) + 1;

        String stt = String.valueOf(count);

        return "DMX" + datePart + stt;
    }

}
