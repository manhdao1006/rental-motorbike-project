package com.ute.rental.controller.khachhang;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.constant.ApiName;
import com.ute.rental.constant.ApiUrl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController(value = "xuLyKhieuNai" + ApiName.WEB)
@RequestMapping(ApiUrl.API_KHACHHANG + "/xu-ly-khieu-nai")
public class XuLyKhieuNaiController {

}
