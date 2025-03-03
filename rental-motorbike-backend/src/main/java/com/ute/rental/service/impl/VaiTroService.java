package com.ute.rental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.VaiTroConverter;
import com.ute.rental.dto.VaiTroDTO;
import com.ute.rental.entity.VaiTroEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.VaiTroRepository;
import com.ute.rental.service.IVaiTroService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaiTroService implements IVaiTroService {

    private final VaiTroRepository vaiTroRepository;
    private final VaiTroConverter vaiTroConverter;

    @Override
    public List<VaiTroDTO> getVaiTros() {
        List<VaiTroEntity> entities = vaiTroRepository.findAll();
        return entities.stream().map(vaiTroConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public VaiTroDTO getVaiTroByMaVaiTro(String maVaiTro) {
        VaiTroEntity vaiTroEntity = vaiTroRepository.findOneByMaVaiTro(maVaiTro)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy vai trò nào với mã vai trò là: " + maVaiTro));
        return vaiTroConverter.toDTO(vaiTroEntity);
    }

    @Transactional
    @Override
    public VaiTroDTO addVaiTro(VaiTroDTO vaiTroDTO) {
        VaiTroEntity vaiTroEntity = vaiTroConverter.toEntity(vaiTroDTO);
        return vaiTroConverter.toDTO(vaiTroRepository.save(vaiTroEntity));
    }

    @Transactional
    @Override
    public VaiTroDTO updateVaiTro(String maVaiTro, VaiTroDTO vaiTroDTO) {
        VaiTroEntity vaiTroEntity = vaiTroRepository.findOneByMaVaiTro(maVaiTro)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy vai trò nào với mã vai trò là: " + maVaiTro));
        VaiTroEntity vaiTroUpdated = vaiTroConverter.toEntity(vaiTroDTO, vaiTroEntity);
        return vaiTroConverter.toDTO(vaiTroRepository.save(vaiTroUpdated));
    }

}
