package com.ute.rental.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ute.rental.converter.AnhXeMayConverter;
import com.ute.rental.converter.ChuCuaHangConverter;
import com.ute.rental.converter.DanhMucXeConverter;
import com.ute.rental.converter.NguoiDungConverter;
import com.ute.rental.converter.XeMayConverter;
import com.ute.rental.dto.AnhXeMayDTO;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.DanhMucXeDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.XeMayDTO;
import com.ute.rental.dto.XeMayResponseDTO;
import com.ute.rental.entity.AnhXeMayEntity;
import com.ute.rental.entity.ChuCuaHangEntity;
import com.ute.rental.entity.DanhMucXeEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.XeMayEntity;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.AnhXeMayRepository;
import com.ute.rental.repository.ChuCuaHangRepository;
import com.ute.rental.repository.DanhMucXeRepository;
import com.ute.rental.repository.XeMayRepository;
import com.ute.rental.service.IXeMayService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XeMayService implements IXeMayService {

    private final XeMayRepository xeMayRepository;
    private final ChuCuaHangRepository chuCuaHangRepository;
    private final DanhMucXeRepository danhMucXeRepository;
    private final AnhXeMayRepository anhXeMayRepository;
    private final XeMayConverter xeMayConverter;
    private final ChuCuaHangConverter chuCuaHangConverter;
    private final DanhMucXeConverter danhMucXeConverter;
    private final NguoiDungConverter nguoiDungConverter;
    private final AnhXeMayConverter anhXeMayConverter;
    private final Cloudinary cloudinary;

    @Override
    public List<XeMayResponseDTO> getXeMaysByMaChuCuaHang(String maChuCuaHang) {
        List<XeMayEntity> entities = xeMayRepository.findXeMaysByChuCuaHang_MaChuCuaHangAndTrangThaiXoa(maChuCuaHang,
                "1");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }

        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMays() {
        List<XeMayEntity> entities = xeMayRepository.findXeMaysByTrangThaiHoatDongAndTrangThaiXoa("Có sẵn", "1");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Transactional
    @Override
    public XeMayDTO addXeMay(XeMayDTO xeMayDTO, List<MultipartFile> anhXeMayList) throws IOException {
        ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(xeMayDTO.getMaChuCuaHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                + xeMayDTO.getMaChuCuaHang()));
        DanhMucXeEntity danhMucXeEntity = danhMucXeRepository.findOneByMaDanhMucXe(xeMayDTO.getMaDanhMucXe())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy danh mục xe nào với mã danh mục xe là: "
                                + xeMayDTO.getMaDanhMucXe()));

        XeMayEntity xeMayEntity = xeMayConverter.toEntity(xeMayDTO);
        xeMayEntity.setMaXeMay(generateMaXeMay());
        xeMayEntity.setChuCuaHang(chuCuaHangEntity);
        xeMayEntity.setDanhMucXe(danhMucXeEntity);
        xeMayEntity = xeMayRepository.save(xeMayEntity);

        // Upload ảnh lên Cloudinary và lưu vào DB
        if (anhXeMayList != null && !anhXeMayList.isEmpty()) {
            for (MultipartFile file : anhXeMayList) {
                Map<String, String> anhXeMayInfo = uploadAnh(file, "xe-may");
                AnhXeMayEntity anhXeMay = new AnhXeMayEntity();
                anhXeMay.setMaAnhXeMay(generateMaAnhXeMay());
                anhXeMay.setTenAnh(anhXeMayInfo.get("publicId"));
                anhXeMay.setDuongDan(anhXeMayInfo.get("url"));
                anhXeMay.setXeMay(xeMayEntity);
                anhXeMayRepository.save(anhXeMay);
            }
        }

        return xeMayConverter.toDTO(xeMayEntity);
    }

    @Transactional
    @Override
    public XeMayDTO updateXeMay(String maXeMay, XeMayDTO xeMayDTO, List<MultipartFile> anhXeMayList,
            List<String> deletedAnhXeMays) throws IOException {
        XeMayEntity xeMayEntity = xeMayRepository.findOneByMaXeMay(maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe máy nào với mã xe máy là: "
                                + maXeMay));

        if (xeMayDTO.getMaChuCuaHang() != null) {
            ChuCuaHangEntity chuCuaHangEntity = chuCuaHangRepository.findOneByMaChuCuaHang(xeMayDTO.getMaChuCuaHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy chủ cửa hàng nào với mã chủ cửa hàng là: "
                                    + xeMayDTO.getMaChuCuaHang()));
            xeMayEntity.setChuCuaHang(chuCuaHangEntity);
        }
        if (xeMayDTO.getMaDanhMucXe() != null) {

            DanhMucXeEntity danhMucXeEntity = danhMucXeRepository
                    .findOneByMaDanhMucXe(xeMayDTO.getMaDanhMucXe())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy danh mục xe nào với mã danh mục xe là: "
                                    + xeMayDTO.getMaDanhMucXe()));
            xeMayEntity.setDanhMucXe(danhMucXeEntity);
        }

        if (deletedAnhXeMays != null && !deletedAnhXeMays.isEmpty()) {
            for (String imageName : deletedAnhXeMays) {
                AnhXeMayEntity anhXeMay = anhXeMayRepository.findOneByTenAnh(imageName)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh: " + imageName));
                cloudinary.uploader().destroy(anhXeMay.getDuongDan(), ObjectUtils.emptyMap());
                xeMayEntity.getAnhXeMays().remove(anhXeMay);
                anhXeMayRepository.delete(anhXeMay);
            }
        }

        if (anhXeMayList != null && !anhXeMayList.isEmpty()) {
            for (MultipartFile file : anhXeMayList) {
                Map<String, String> anhXeMayInfo = uploadAnh(file, "xe-may");
                AnhXeMayEntity anhXeMay = new AnhXeMayEntity();
                anhXeMay.setMaAnhXeMay(generateMaAnhXeMay());
                anhXeMay.setTenAnh(anhXeMayInfo.get("publicId"));
                anhXeMay.setDuongDan(anhXeMayInfo.get("url"));
                anhXeMay.setXeMay(xeMayEntity);
                anhXeMayRepository.save(anhXeMay);
            }
        }

        XeMayEntity xeMayUpdated = xeMayConverter.toEntity(xeMayDTO, xeMayEntity);
        return xeMayConverter.toDTO(xeMayRepository.save(xeMayUpdated));
    }

    @Transactional
    @Override
    public void deleteXeMay(String maXeMay) {
        XeMayEntity xeMayEntity = xeMayRepository.findOneByMaXeMay(maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe máy nào với mã xe máy là: "
                                + maXeMay));
        xeMayEntity.setTrangThaiXoa("0");
        xeMayRepository.save(xeMayEntity);
    }

    @SuppressWarnings({ "null", "unchecked" })
    private Map<String, String> uploadAnh(MultipartFile file, String folderName) throws IOException {
        Map<String, String> fileInfo = new HashMap<>();

        // check valid image
        if (file == null || file.isEmpty()) {
            fileInfo.put("publicId", null);
            fileInfo.put("url", null);
        } else {
            if (!file.getContentType().startsWith("image/")) {
                throw new ResourceNotFormatException("Phải là file ảnh!");
            }
            // upload image
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", folderName));

            // get info from cloudinary
            String publicId = (String) result.get("public_id");
            String url = (String) result.get("url");

            fileInfo.put("publicId", publicId);
            fileInfo.put("url", url);
        }

        return fileInfo;
    }

    @Override
    public XeMayResponseDTO getXeMayByMaXeMay(String maXeMay) {
        XeMayEntity xeMayEntity = xeMayRepository.findOneByMaXeMay(maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe máy nào với mã xe máy là: "
                                + maXeMay));
        XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

        ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
        ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

        NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
        NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

        DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
        DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

        List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
        List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

        return new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs);
    }

    private String generateMaXeMay() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = xeMayRepository.countByMaXeMayStartingWith("XM" + datePart) + 1;

        String stt = String.valueOf(count);

        return "XM" + datePart + stt;
    }

    private String generateMaAnhXeMay() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = anhXeMayRepository.countByMaAnhXeMayStartingWith("AXM" + datePart) + 1;

        String stt = String.valueOf(count);

        return "AXM" + datePart + stt;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysInQuanHaiChau() {
        List<XeMayEntity> entities = xeMayRepository
                .findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
                        "QH01", "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysInQuanThanhKhe() {
        List<XeMayEntity> entities = xeMayRepository
                .findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
                        "QH02", "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysInQuanSonTra() {
        List<XeMayEntity> entities = xeMayRepository
                .findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
                        "QH03", "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysInQuanNguHanhSon() {
        List<XeMayEntity> entities = xeMayRepository
                .findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
                        "QH04", "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysInQuanLienChieu() {
        List<XeMayEntity> entities = xeMayRepository
                .findFirst8ByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByGiaThueDesc(
                        "QH05", "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Override
    public List<XeMayResponseDTO> getXeMaysByQuanHuyen(String maQuanHuyen) {
        List<XeMayEntity> entities = xeMayRepository
                .findXeMaysByChuCuaHang_PhuongXa_QuanHuyen_MaQuanHuyenAndTrangThaiXoaAndTrangThaiHoatDongOrderByMaXeMayDesc(
                        maQuanHuyen, "1", "Có sẵn");
        List<XeMayResponseDTO> responseList = new ArrayList<>();
        for (XeMayEntity xeMayEntity : entities) {
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            ChuCuaHangEntity chuCuaHangEntity = xeMayEntity.getChuCuaHang();
            ChuCuaHangDTO chuCuaHangDTO = chuCuaHangConverter.toDTO(chuCuaHangEntity);

            NguoiDungEntity nguoiDungEntity = xeMayEntity.getChuCuaHang().getNguoiDung();
            NguoiDungDTO nguoiDungDTO = nguoiDungConverter.toDTO(nguoiDungEntity);

            DanhMucXeEntity danhMucXeEntity = xeMayEntity.getDanhMucXe();
            DanhMucXeDTO danhMucXeDTO = danhMucXeConverter.toDTO(danhMucXeEntity);

            List<AnhXeMayEntity> anhXeMayEntities = xeMayEntity.getAnhXeMays();
            List<AnhXeMayDTO> anhXeMayDTOs = anhXeMayConverter.toDTOs(anhXeMayEntities);

            responseList.add(new XeMayResponseDTO(xeMayDTO, danhMucXeDTO, chuCuaHangDTO, nguoiDungDTO, anhXeMayDTOs));
        }
        return responseList;
    }

    @Transactional
    @Override
    public void capNhatTrangThaiXeMay(String maXeMay, String trangThaiHoatDong) {
        XeMayEntity xeMayEntity = xeMayRepository.findOneByMaXeMay(maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe máy nào với mã xe máy là: "
                                + maXeMay));
        xeMayEntity.setTrangThaiHoatDong(trangThaiHoatDong);
        xeMayRepository.save(xeMayEntity);
    }

}
