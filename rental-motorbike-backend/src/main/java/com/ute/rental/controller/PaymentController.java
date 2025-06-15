package com.ute.rental.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.config.PaymentConfig;
import com.ute.rental.dto.PaymentResponseDTO;
import com.ute.rental.entity.DonHangEntity;
import com.ute.rental.exception.ResourceNotFoundException;
import com.ute.rental.repository.DonHangRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/payment")
public class PaymentController {

    private final DonHangRepository donHangRepository;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestParam(value = "vnp_Amount") long amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            HttpServletRequest req)
            throws UnsupportedEncodingException {

        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + txnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", PaymentConfig.getIpAddress(req));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=");
        query.append(vnp_SecureHash);

        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + query.toString();

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setStatus("Ok");
        paymentResponseDTO.setMessage("Thành công");
        paymentResponseDTO.setUrl(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTO);
    }

    @GetMapping("/payment-info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_TxnRef") String txnRef,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_TransactionNo") String transactionNo) {

        String[] parts = txnRef.split("-");
        String maDonHang = parts[0];
        String loaiThanhToan = parts.length > 1 ? parts[1] : "FULL";

        DonHangEntity donHangEntity = donHangRepository.findOneByMaDonHang(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đơn hàng nào với mã đơn hàng là: "
                                + txnRef));

        if (responseCode.equals("00")) {
            if (loaiThanhToan.equalsIgnoreCase("DATCOC")) {
                donHangEntity.setTrangThaiThanhToan("Đặt cọc");
            } else if (loaiThanhToan.equalsIgnoreCase("THANHTOANCONLAI")) {
                donHangEntity.setTrangThaiThanhToan("Đã thanh toán");
            } else {
                donHangEntity.setTrangThaiThanhToan("Đã thanh toán");
            }

            donHangEntity.setMaGiaoDich(transactionNo);
            donHangRepository.save(donHangEntity);
        }

        String redirectUrl = "http://localhost:3000/trang-chu";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundDeposit(
            @RequestParam String maDonHang,
            @RequestParam long soTienHoan) {

        DonHangEntity donHang = donHangRepository.findOneByMaDonHang(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));

        String trangThai = donHang.getTrangThaiThanhToan();
        if (!trangThai.equalsIgnoreCase("Đặt cọc") &&
                !trangThai.equalsIgnoreCase("Đã thanh toán")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Chỉ hoàn được đơn hàng đã đặt cọc hoặc đã thanh toán");
        }

        String loaiThanhToan = trangThai.equalsIgnoreCase("Đặt cọc") ? "DATCOC" : "THANHTOANTOANBO";
        String vnp_TxnRef = donHang.getMaDonHang() + "-" + loaiThanhToan;
        long refundAmount = soTienHoan * 100;
        String vnp_TransactionNo = donHang.getMaGiaoDich();

        boolean success = vnpayRefundRequest(vnp_TxnRef, refundAmount, vnp_TransactionNo);

        if (success) {
            donHang.setTrangThaiThanhToan(loaiThanhToan.equals("DATCOC") ? "Hoàn cọc" : "Hoàn toàn bộ");
            donHangRepository.save(donHang);
            return ResponseEntity.ok("Hoàn cọc thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hoàn cọc thất bại");
        }
    }

    public boolean vnpayRefundRequest(String vnp_TxnRef, long amount, String vnp_TransactionNo) {
        try {
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_RequestId", String.valueOf(System.currentTimeMillis()));
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "refund");
            vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_TransactionType", "02");
            vnp_Params.put("vnp_CreateBy", "admin");
            vnp_Params.put("vnp_OrderInfo", "Hoan coc don hang: " + vnp_TxnRef);
            vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
            vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

            String hashData = PaymentConfig.hashAllFields(vnp_Params);
            vnp_Params.put("vnp_SecureHash", hashData);

            System.out.println("Send refund to VNPAY: " + vnp_Params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
