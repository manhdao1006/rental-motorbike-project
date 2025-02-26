package com.ute.rental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.QuanHuyenConverter;
import com.ute.rental.dto.QuanHuyenDTO;
import com.ute.rental.entity.QuanHuyenEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.QuanHuyenRepository;
import com.ute.rental.service.IQuanHuyenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuanHuyenService implements IQuanHuyenService {

    private final QuanHuyenRepository quanHuyenRepository;
    private final QuanHuyenConverter quanHuyenConverter;

    @Override
    public List<QuanHuyenDTO> getQuanHuyens() {
        List<QuanHuyenEntity> entities = quanHuyenRepository.findAll();
        return entities.stream().map(quanHuyenConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public QuanHuyenDTO getQuanHuyenByMaPhuongXa(String maPhuongXa) {
        QuanHuyenEntity quanHuyenEntity = quanHuyenRepository.findByMaPhuongXa(maPhuongXa)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy quận huyện nào với mã phường xã là " + maPhuongXa));
        return quanHuyenConverter.toDTO(quanHuyenEntity);
    }

}
