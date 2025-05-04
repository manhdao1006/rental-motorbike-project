package com.ute.rental.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ute.rental.converter.ChiTietDonHangConverter;
import com.ute.rental.converter.DonHangConverter;
import com.ute.rental.converter.XeMayConverter;
import com.ute.rental.dto.ChiTietDonHangDTO;
import com.ute.rental.dto.ChiTietDonHangResponseDTO;
import com.ute.rental.dto.DonHangDTO;
import com.ute.rental.dto.XeMayDTO;
import com.ute.rental.entity.ChiTietDonHangEntity;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.entity.XeMayEntity;
import com.ute.rental.entity.impl.MaChiTietDonHang;
import com.ute.rental.exception.ResourceNotFormatException;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.ChiTietDonHangRepository;
import com.ute.rental.repository.DonHangRepository;
import com.ute.rental.repository.XeMayRepository;
import com.ute.rental.service.IChiTietDonHangService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangService implements IChiTietDonHangService {

    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final XeMayRepository xeMayRepository;
    private final ChiTietDonHangConverter chiTietDonHangConverter;
    private final DonHangConverter donHangConverter;
    private final XeMayConverter xeMayConverter;
    private final Cloudinary cloudinary;

    @Override
    public List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaDonHang(String maDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByDonHang_MaDonHangAndTrangThaiXoa(maDonHang, "1");
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getChiTietDonHangs() {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository.findChiTietDonHangsByTrangThaiXoa("1");
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Transactional
    @Override
    public ChiTietDonHangDTO addChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO) {
        DonHangEntity donHangEntity = donHangRepository
                .findOneByMaDonHang(chiTietDonHangDTO.getMaDonHang())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                + chiTietDonHangDTO.getMaDonHang()));
        XeMayEntity xeMayEntity = xeMayRepository.findOneByMaXeMay(chiTietDonHangDTO.getMaXeMay())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe máy nào với mã xe máy là: "
                                + chiTietDonHangDTO.getMaXeMay()));
        ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangConverter.toEntity(chiTietDonHangDTO);
        chiTietDonHangEntity
                .setMaChiTietDonHang(new MaChiTietDonHang(donHangEntity.getMaDonHang(), xeMayEntity.getMaXeMay()));
        chiTietDonHangEntity.setDonHang(donHangEntity);
        chiTietDonHangEntity.setXeMay(xeMayEntity);
        chiTietDonHangEntity = chiTietDonHangRepository.save(chiTietDonHangEntity);

        return chiTietDonHangConverter.toDTO(chiTietDonHangEntity);
    }

    @Transactional
    @Override
    public ChiTietDonHangDTO updateChiTietDonHang(String maDonHang, String maXeMay,
            ChiTietDonHangDTO chiTietDonHangDTO, MultipartFile hinhAnhTruocThue, MultipartFile hinhAnhSauThue)
            throws IOException {
        ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangRepository
                .findOneByDonHang_MaDonHangAndXeMay_MaXeMay(maDonHang, maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chi tiết đơn hàng nào với mã đơn hàng là: " + maDonHang + " và mã xe máy là: "
                                + maXeMay));

        if (chiTietDonHangDTO.getMaDonHang() != null) {
            DonHangEntity donHangEntity = donHangRepository
                    .findOneByMaDonHang(chiTietDonHangDTO.getMaDonHang())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                    + chiTietDonHangDTO.getMaDonHang()));
            chiTietDonHangEntity.setDonHang(donHangEntity);
        }
        if (chiTietDonHangDTO.getMaXeMay() != null) {
            XeMayEntity xeMayEntity = xeMayRepository
                    .findOneByMaXeMay(chiTietDonHangDTO.getMaXeMay())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy xe máy nào với mã xe máy là: "
                                    + chiTietDonHangDTO.getMaXeMay()));
            chiTietDonHangEntity.setXeMay(xeMayEntity);
        }

        if (hinhAnhTruocThue != null && !hinhAnhTruocThue.isEmpty()) {
            Map<String, String> hinhAnhInfo = uploadAnh(hinhAnhTruocThue, "tinh-trang-xe-may");
            chiTietDonHangEntity.setHinhAnhTruocThue(hinhAnhInfo.get("url"));
        }

        if (hinhAnhSauThue != null && !hinhAnhSauThue.isEmpty()) {
            Map<String, String> hinhAnhInfo = uploadAnh(hinhAnhSauThue, "tinh-trang-xe-may");
            chiTietDonHangEntity.setHinhAnhSauThue(hinhAnhInfo.get("url"));
        }

        ChiTietDonHangEntity chiTietDonHangUpdated = chiTietDonHangConverter.toEntity(chiTietDonHangDTO,
                chiTietDonHangEntity);
        return chiTietDonHangConverter.toDTO(chiTietDonHangRepository.save(chiTietDonHangUpdated));
    }

    @Transactional
    @Override
    public void deleteChiTietDonHang(String maDonHang, String maXeMay) {
        ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangRepository
                .findOneByDonHang_MaDonHangAndXeMay_MaXeMay(maDonHang, maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chi tiết đơn hàng nào với mã đơn hàng là: " + maDonHang + " và mã xe máy là: "
                                + maXeMay));
        chiTietDonHangEntity.setTrangThaiXoa("0");
        chiTietDonHangRepository.save(chiTietDonHangEntity);
    }

    @Override
    public ChiTietDonHangResponseDTO getChiTietDonHangByMaDonHangAndMaXeMay(String maDonHang, String maXeMay) {
        ChiTietDonHangEntity chiTietDonHangEntity = chiTietDonHangRepository
                .findOneByDonHang_MaDonHangAndXeMay_MaXeMay(maDonHang, maXeMay)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chi tiết đơn hàng nào với mã đơn hàng là: " + maDonHang + " và mã xe máy là: "
                                + maXeMay));
        ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

        DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
        DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

        XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
        XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

        return new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO);
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaKhachHang(String maKhachHang,
            String trangThaiDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHangAndTrangThaiXoa(
                        maKhachHang, trangThaiDonHang, "1");
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangKhachHang(String maNguoiDung, String trangThaiDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByDonHang_KhachHang_MaKhachHangAndDonHang_TrangThaiDonHang(maNguoiDung,
                        trangThaiDonHang);
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaNhanVien(String maNhanVien, String trangThaiDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByDonHang_NhanVien_MaNhanVienAndDonHang_TrangThaiDonHangAndTrangThaiXoa(maNhanVien,
                        trangThaiDonHang, "1");
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getByTrangThaiDonHangNhanVien(String maNguoiDung,
            String trangThaiDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByDonHang_NhanVien_MaNhanVienAndDonHang_TrangThaiDonHang(maNguoiDung,
                        trangThaiDonHang);
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
    }

    @Override
    public List<ChiTietDonHangResponseDTO> getChiTietDonHangsByMaChuCuaHang(String maChuCuaHang,
            String trangThaiDonHang) {
        List<ChiTietDonHangEntity> entities = chiTietDonHangRepository
                .findChiTietDonHangsByXeMay_ChuCuaHang_MaChuCuaHangAndDonHang_TrangThaiDonHangAndTrangThaiXoa(
                        maChuCuaHang, trangThaiDonHang, "1");
        List<ChiTietDonHangResponseDTO> responseList = new ArrayList<>();
        for (ChiTietDonHangEntity chiTietDonHangEntity : entities) {
            ChiTietDonHangDTO chiTietDonHangDTO = chiTietDonHangConverter.toDTO(chiTietDonHangEntity);

            DonHangEntity donHangEntity = chiTietDonHangEntity.getDonHang();
            DonHangDTO donHangDTO = donHangConverter.toDTO(donHangEntity);

            XeMayEntity xeMayEntity = chiTietDonHangEntity.getXeMay();
            XeMayDTO xeMayDTO = xeMayConverter.toDTO(xeMayEntity);

            responseList.add(new ChiTietDonHangResponseDTO(chiTietDonHangDTO, donHangDTO, xeMayDTO));
        }

        return responseList;
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

}
