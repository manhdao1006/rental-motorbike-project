package com.ute.rental.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ute.rental.converter.KhieuNaiConverter;
import com.ute.rental.converter.NhanVienConverter;
import com.ute.rental.converter.XuLyKhieuNaiConverter;
import com.ute.rental.dto.KhieuNaiDTO;
import com.ute.rental.dto.NhanVienDTO;
import com.ute.rental.dto.XuLyKhieuNaiDTO;
import com.ute.rental.dto.XuLyKhieuNaiResponseDTO;
import com.ute.rental.entity.KhieuNaiEntity;
import com.ute.rental.entity.NhanVienEntity;
import com.ute.rental.entity.XuLyKhieuNaiEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.KhieuNaiRepository;
import com.ute.rental.repository.NhanVienRepository;
import com.ute.rental.repository.XuLyKhieuNaiRepository;
import com.ute.rental.service.IXuLyKhieuNaiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XuLyKhieuNaiService implements IXuLyKhieuNaiService {

    private final KhieuNaiRepository khieuNaiRepository;
    private final NhanVienRepository nhanVienRepository;
    private final XuLyKhieuNaiRepository xuLyKhieuNaiRepository;
    private final KhieuNaiConverter khieuNaiConverter;
    private final NhanVienConverter nhanVienConverter;
    private final XuLyKhieuNaiConverter xuLyKhieuNaiConverter;

    @Override
    public List<XuLyKhieuNaiResponseDTO> getXuLyKhieuNais() {
        List<XuLyKhieuNaiEntity> entities = xuLyKhieuNaiRepository
                .findXuLyKhieuNaisByTrangThaiXoa("1");
        List<XuLyKhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (XuLyKhieuNaiEntity xuLyKhieuNaiEntity : entities) {
            XuLyKhieuNaiDTO xuLyKhieuNaiDTO = xuLyKhieuNaiConverter.toDTO(xuLyKhieuNaiEntity);

            NhanVienEntity nhanVienEntity = xuLyKhieuNaiEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            KhieuNaiEntity khieuNaiEntity = xuLyKhieuNaiEntity.getKhieuNai();
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            responseList.add(new XuLyKhieuNaiResponseDTO(xuLyKhieuNaiDTO, nhanVienDTO, khieuNaiDTO));
        }
        return responseList;
    }

    @Override
    public List<XuLyKhieuNaiResponseDTO> getXuLyKhieuNaisByMaNhanVien(String maNhanVien) {
        List<XuLyKhieuNaiEntity> entities = xuLyKhieuNaiRepository
                .findXuLyKhieuNaisByTrangThaiXoaAndNhanVien_MaNhanVien("1", maNhanVien);
        List<XuLyKhieuNaiResponseDTO> responseList = new ArrayList<>();
        for (XuLyKhieuNaiEntity xuLyKhieuNaiEntity : entities) {
            XuLyKhieuNaiDTO xuLyKhieuNaiDTO = xuLyKhieuNaiConverter.toDTO(xuLyKhieuNaiEntity);

            NhanVienEntity nhanVienEntity = xuLyKhieuNaiEntity.getNhanVien();
            NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

            KhieuNaiEntity khieuNaiEntity = xuLyKhieuNaiEntity.getKhieuNai();
            KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

            responseList.add(new XuLyKhieuNaiResponseDTO(xuLyKhieuNaiDTO, nhanVienDTO, khieuNaiDTO));
        }
        return responseList;
    }

    @Transactional
    @Override
    public XuLyKhieuNaiDTO addXuLyKhieuNai(XuLyKhieuNaiDTO xuLyKhieuNaiDTO) {
        KhieuNaiEntity KhieuNaiEntity = khieuNaiRepository
                .findOneByMaKhieuNai(xuLyKhieuNaiDTO.getMaKhieuNai())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là "
                                + xuLyKhieuNaiDTO.getMaKhieuNai()));
        NhanVienEntity nhanVienEntity = nhanVienRepository
                .findOneByMaNhanVien(xuLyKhieuNaiDTO.getMaNhanVien())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy nhân viên nào với mã nhân viên là "
                                + xuLyKhieuNaiDTO.getMaKhieuNai()));

        XuLyKhieuNaiEntity xuLyKhieuNaiEntity = xuLyKhieuNaiConverter.toEntity(xuLyKhieuNaiDTO);
        xuLyKhieuNaiEntity.setMaXuLyKhieuNai(generateMaXuLyKhieuNai());
        xuLyKhieuNaiEntity.setKhieuNai(KhieuNaiEntity);
        xuLyKhieuNaiEntity.setNhanVien(nhanVienEntity);
        return xuLyKhieuNaiConverter.toDTO(xuLyKhieuNaiRepository.save(xuLyKhieuNaiEntity));
    }

    @Transactional
    @Override
    public XuLyKhieuNaiDTO updateXuLyKhieuNai(String maXuLyKhieuNai,
            XuLyKhieuNaiDTO updatedXuLyKhieuNai) {
        XuLyKhieuNaiEntity xuLyKhieuNaiEntity = xuLyKhieuNaiRepository.findOneByMaXuLyKhieuNai(maXuLyKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xử lý khiếu nại nào với mã xử lý khiếu nại là " + maXuLyKhieuNai));

        if (updatedXuLyKhieuNai.getMaKhieuNai() != null) {
            KhieuNaiEntity KhieuNaiEntity = khieuNaiRepository
                    .findOneByMaKhieuNai(updatedXuLyKhieuNai.getMaKhieuNai())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy khiếu nại nào với mã khiếu nại là "
                                    + updatedXuLyKhieuNai.getMaKhieuNai()));
            xuLyKhieuNaiEntity.setKhieuNai(KhieuNaiEntity);
        }
        if (updatedXuLyKhieuNai.getMaNhanVien() != null) {
            NhanVienEntity nhanVienEntity = nhanVienRepository
                    .findOneByMaNhanVien(updatedXuLyKhieuNai.getMaNhanVien())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy nhân viên nào với mã nhân viên là "
                                    + updatedXuLyKhieuNai.getMaKhieuNai()));
            xuLyKhieuNaiEntity.setNhanVien(nhanVienEntity);
        }

        XuLyKhieuNaiEntity xuLyKhieuNaiUpdated = xuLyKhieuNaiConverter.toEntity(updatedXuLyKhieuNai,
                xuLyKhieuNaiEntity);
        return xuLyKhieuNaiConverter.toDTO(xuLyKhieuNaiRepository.save(xuLyKhieuNaiUpdated));
    }

    @Transactional
    @Override
    public void deleteXuLyKhieuNai(String maXuLyKhieuNai) {
        XuLyKhieuNaiEntity xuLyKhieuNaiEntity = xuLyKhieuNaiRepository.findOneByMaXuLyKhieuNai(maXuLyKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy khiếu nại nào với mã khiếu nại là " + maXuLyKhieuNai));
        xuLyKhieuNaiEntity.setTrangThaiXoa("0");
        xuLyKhieuNaiRepository.save(xuLyKhieuNaiEntity);
    }

    @Override
    public XuLyKhieuNaiResponseDTO getXuLyKhieuNaiByMaXuLyKhieuNai(String maXuLyKhieuNai) {
        XuLyKhieuNaiEntity xuLyKhieuNaiEntity = xuLyKhieuNaiRepository.findOneByMaXuLyKhieuNai(maXuLyKhieuNai)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xử lý khiếu nại nào với mã xử lý khiếu nại là " + maXuLyKhieuNai));
        XuLyKhieuNaiDTO xuLyKhieuNaiDTO = xuLyKhieuNaiConverter.toDTO(xuLyKhieuNaiEntity);

        NhanVienEntity nhanVienEntity = xuLyKhieuNaiEntity.getNhanVien();
        NhanVienDTO nhanVienDTO = nhanVienConverter.toDTO(nhanVienEntity);

        KhieuNaiEntity khieuNaiEntity = xuLyKhieuNaiEntity.getKhieuNai();
        KhieuNaiDTO khieuNaiDTO = khieuNaiConverter.toDTO(khieuNaiEntity);

        return new XuLyKhieuNaiResponseDTO(xuLyKhieuNaiDTO, nhanVienDTO, khieuNaiDTO);
    }

    private String generateMaXuLyKhieuNai() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = xuLyKhieuNaiRepository.countByMaXuLyKhieuNaiStartingWith("XLKN" + datePart) + 1;

        String stt = String.valueOf(count);

        return "XLKN" + datePart + stt;
    }

}
