package com.ute.rental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.PhuongXaConverter;
import com.ute.rental.dto.PhuongXaDTO;
import com.ute.rental.entity.PhuongXaEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.PhuongXaRepository;
import com.ute.rental.service.IPhuongXaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhuongXaService implements IPhuongXaService {

    private final PhuongXaRepository phuongXaRepository;
    private final PhuongXaConverter phuongXaConverter;

    @Override
    public List<PhuongXaDTO> getPhuongXas() {
        List<PhuongXaEntity> entities = phuongXaRepository.findAll();
        return entities.stream().map(phuongXaConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PhuongXaDTO> getPhuongXasByMaQuanHuyen(String maQuanHuyen) {
        List<PhuongXaEntity> entities = phuongXaRepository.findPhuongXasByQuanHuyen_MaQuanHuyen(maQuanHuyen);
        return entities.stream().map(phuongXaConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public PhuongXaDTO getPhuongXaByMaPhuongXa(String maPhuongXa) {
        PhuongXaEntity phuongXaEntity = phuongXaRepository.findOneByMaPhuongXa(maPhuongXa)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phường xã nào với mã phường xã là " + maPhuongXa));
        return phuongXaConverter.toDTO(phuongXaEntity);
    }

}
