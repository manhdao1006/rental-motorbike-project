package com.ute.rental.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ute.rental.dto.ApiResponse;
import com.ute.rental.dto.AuthResponseDTO;
import com.ute.rental.dto.ChuCuaHangDTO;
import com.ute.rental.dto.ChuCuaHangResponseDTO;
import com.ute.rental.dto.DanhMucXeDTO;
import com.ute.rental.dto.DonHangResponseDTO;
import com.ute.rental.dto.EmailRequest;
import com.ute.rental.dto.LoaiKhieuNaiDTO;
import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NguoiDungResponseDTO;
import com.ute.rental.dto.PhuongXaDTO;
import com.ute.rental.dto.QuanHuyenDTO;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.security.BlackList;
import com.ute.rental.security.CustomUserDetailsService;
import com.ute.rental.security.JWTGenerator;
import com.ute.rental.service.IChuCuaHangService;
import com.ute.rental.service.IDanhMucXeService;
import com.ute.rental.service.IDonHangService;
import com.ute.rental.service.IEmailService;
import com.ute.rental.service.ILoaiKhieuNaiService;
import com.ute.rental.service.INguoiDungService;
import com.ute.rental.service.IPhuongXaService;
import com.ute.rental.service.IQuanHuyenService;
import com.ute.rental.service.impl.WordService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final INguoiDungService nguoiDungService;
    private final JWTGenerator jwtGenerator;
    private final BlackList blackList;
    private final CustomUserDetailsService userDetailsService;
    private final IPhuongXaService phuongXaService;
    private final IQuanHuyenService quanHuyenService;
    private final WordService wordService;
    private final IChuCuaHangService chuCuaHangService;
    private final NguoiDungRepository nguoiDungRepository;
    private final IEmailService emailService;
    private final IDanhMucXeService danhMucXeService;
    private final ILoaiKhieuNaiService loaiKhieuNaiService;
    private final IDonHangService donHangService;

    @GetMapping("/maDonHang/{maDonHang}")
    public ApiResponse<DonHangResponseDTO> getDonHangByMaDonHang(
            @PathVariable("maDonHang") String maDonHang) {
        return ApiResponse.<DonHangResponseDTO>builder()
                .code(200)
                .message("Danh sách đơn hàng")
                .result(donHangService.getDonHangByMaDonHang(maDonHang))
                .build();
    }

    @GetMapping("/loai-khieu-nai/list")
    public ApiResponse<List<LoaiKhieuNaiDTO>> getLoaiKhieuNais() {
        return ApiResponse.<List<LoaiKhieuNaiDTO>>builder()
                .code(200)
                .message("Danh sách loại khiếu nại")
                .result(loaiKhieuNaiService.getLoaiKhieuNais())
                .build();
    }

    @GetMapping("/maLoaiKhieuNai/{maLoaiKhieuNai}")
    public ApiResponse<LoaiKhieuNaiDTO> getLoaiKhieuNaiByMaLoaiKhieuNai(
            @PathVariable("maLoaiKhieuNai") String maLoaiKhieuNai) {
        return ApiResponse.<LoaiKhieuNaiDTO>builder()
                .code(200)
                .message("Loại khiếu nại với mã loại khiếu nại là " + maLoaiKhieuNai)
                .result(loaiKhieuNaiService.getLoaiKhieuNaiByMaLoaiKhieuNai(maLoaiKhieuNai))
                .build();
    }

    @GetMapping("/danh-muc-xe/list")
    public ApiResponse<List<DanhMucXeDTO>> getDanhMucXes() {
        return ApiResponse.<List<DanhMucXeDTO>>builder()
                .code(200)
                .message("Danh sách danh mục xe")
                .result(danhMucXeService.getDanhMucXes())
                .build();
    }

    @GetMapping("/maDanhMucXe/{maDanhMucXe}")
    public ApiResponse<DanhMucXeDTO> getDanhMucXeByMaDanhMucXe(@PathVariable("maDanhMucXe") String maDanhMucXe) {
        return ApiResponse.<DanhMucXeDTO>builder()
                .code(200)
                .message("Danh mục xe với mã danh mục xe là " + maDanhMucXe)
                .result(danhMucXeService.getDanhMucXeByMaDanhMucXe(maDanhMucXe))
                .build();
    }

    @PostMapping("/send-email")
    public ApiResponse<String> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request.getEmail(), request.getNoiDung(), request.getTieuDe());
        return ApiResponse.<String>builder().message("Gửi email thành công!").build();
    }

    @GetMapping("/login-google")
    public ResponseEntity<AuthResponseDTO> googleLogin(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        if (oAuth2AuthenticationToken == null || oAuth2AuthenticationToken.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Người dùng không tồn tại"));

        // Load lại UserDetails và tạo token mới
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = jwtGenerator.generateToken(userDetails);
        String refreshToken = jwtGenerator.generateRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/dangKyChuCuaHang")
    public ApiResponse<ChuCuaHangResponseDTO> dangKyChuCuaHang(
            @ModelAttribute NguoiDungDTO nguoiDungDTO,
            @ModelAttribute ChuCuaHangDTO chuCuaHangDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ApiResponse.<ChuCuaHangResponseDTO>builder()
                .code(200)
                .message("Thêm mới thành công")
                .result(chuCuaHangService.dangKyChuCuaHang(nguoiDungDTO, chuCuaHangDTO, file))
                .build();
    }

    @PostMapping("/upload-word")
    public ResponseEntity<String> uploadWord(@RequestBody Map<String, Object> formData) {
        String wordUrl = wordService.generateAndUploadWord(formData);
        return ResponseEntity.ok(wordUrl);
    }

    @GetMapping("/quan-huyen")
    public ApiResponse<List<QuanHuyenDTO>> getQuanHuyens() {
        return ApiResponse.<List<QuanHuyenDTO>>builder()
                .code(200)
                .message("Danh sách quận huyện")
                .result(quanHuyenService.getQuanHuyens())
                .build();
    }

    @GetMapping("/quan-huyen/maPhuongXa/{maPhuongXa}")
    public ApiResponse<QuanHuyenDTO> getQuanHuyenByMaPhuongXa(@PathVariable("maPhuongXa") String maPhuongXa) {
        return ApiResponse.<QuanHuyenDTO>builder()
                .code(200)
                .message("Danh sách quận huyện")
                .result(quanHuyenService.getQuanHuyenByMaPhuongXa(maPhuongXa))
                .build();
    }

    @GetMapping("/phuong-xa/maQuanHuyen/{maQuanHuyen}")
    public ApiResponse<List<PhuongXaDTO>> getPhuongXasByMaQuanHuyen(@PathVariable("maQuanHuyen") String maQuanHuyen) {
        return ApiResponse.<List<PhuongXaDTO>>builder()
                .code(200)
                .message("Danh sách phường xã")
                .result(phuongXaService.getPhuongXasByMaQuanHuyen(maQuanHuyen))
                .build();
    }

    @GetMapping("/phuong-xa/maPhuongXa/{maPhuongXa}")
    public ApiResponse<PhuongXaDTO> getPhuongXaByMaPhuongXa(@PathVariable("maPhuongXa") String maPhuongXa) {
        return ApiResponse.<PhuongXaDTO>builder()
                .code(200)
                .message("Phường xã với mã là: " + maPhuongXa)
                .result(phuongXaService.getPhuongXaByMaPhuongXa(maPhuongXa))
                .build();
    }

    @GetMapping("/phuong-xa")
    public ApiResponse<List<PhuongXaDTO>> getPhuongXas() {
        return ApiResponse.<List<PhuongXaDTO>>builder()
                .code(200)
                .message("Danh sách phường xã")
                .result(phuongXaService.getPhuongXas())
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<NguoiDungDTO> getUserProfile(Authentication authentication) {
        return ApiResponse.<NguoiDungDTO>builder()
                .code(200)
                .message("Người dùng với email là " + authentication.getName())
                .result(nguoiDungService.getNguoiDungByEmail(authentication.getName()))
                .build();
    }

    @GetMapping("/view/{maNguoiDung}")
    public ApiResponse<NguoiDungResponseDTO> getNguoiDungByMaNguoiDung(
            @PathVariable("maNguoiDung") String maNguoiDung) {
        return ApiResponse.<NguoiDungResponseDTO>builder()
                .code(200)
                .message("Người dùng với mã người dùng là " + maNguoiDung)
                .result(nguoiDungService.getNguoiDungByMaNguoiDung(maNguoiDung))
                .build();
    }

    @PutMapping("forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email, @RequestParam String password) {
        nguoiDungService.forgotPassword(email, password);
        return ResponseEntity.ok("Password changed successfully!");
    }

    @PostMapping("/register")
    public ApiResponse<NguoiDungDTO> register(@RequestBody NguoiDungDTO nguoiDungDTO) {
        return ApiResponse.<NguoiDungDTO>builder()
                .code(200)
                .message("Đăng ký thành công")
                .result(nguoiDungService.register(nguoiDungDTO))
                .build();
    }

    @PostMapping("/register-admin")
    public ApiResponse<NguoiDungDTO> registerAdmin(@RequestBody NguoiDungDTO nguoiDungDTO) {
        return ApiResponse.<NguoiDungDTO>builder()
                .code(200)
                .message("Đăng ký thành công")
                .result(nguoiDungService.registerAdmin(nguoiDungDTO))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(@RequestBody NguoiDungDTO nguoiDungDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(nguoiDungDTO.getEmail(), nguoiDungDTO.getMatKhau()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtGenerator.generateToken(userDetails);
        String refreshToken = jwtGenerator.generateRefreshToken(userDetails.getUsername());

        return ApiResponse.<AuthResponseDTO>builder()
                .code(200)
                .message("Đăng nhập thành công")
                .result(new AuthResponseDTO(accessToken, refreshToken))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        String jwtToken = token.substring(7);
        if (!jwtGenerator.validateToken(jwtToken)) {
            return ApiResponse.<String>builder()
                    .code(401)
                    .message("Unauthorized: Invalid token.")
                    .build();
        }
        blackList.addToBlackListToken(jwtToken);

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ApiResponse.<String>builder()
                .code(200)
                .message("Đăng xuất thành công")
                .result("You have been logged out.")
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<?> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        if (jwtGenerator.validateRefreshToken(refreshToken)) {
            String username = jwtGenerator.getUsernameFromJWT(refreshToken);

            blackList.addToBlackListToken(refreshToken);
            SecurityContextHolder.clearContext();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtGenerator.generateToken(userDetails);
            String newRefreshToken = jwtGenerator.generateRefreshToken(username);
            return ApiResponse.<AuthResponseDTO>builder()
                    .code(200)
                    .message("Token refreshed successfully")
                    .result(new AuthResponseDTO(newAccessToken, newRefreshToken))
                    .build();
        }

        return ApiResponse.<String>builder()
                .code(401)
                .message("Invalid refresh token")
                .build();
    }

}
