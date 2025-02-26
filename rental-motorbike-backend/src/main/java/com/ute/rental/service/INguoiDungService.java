package com.ute.rental.service;

import java.util.List;

import com.ute.rental.dto.NguoiDungDTO;
import com.ute.rental.dto.NguoiDungResponseDTO;

public interface INguoiDungService {

    void forgotPassword(String email, String password);

    NguoiDungDTO register(NguoiDungDTO userDTO);

    NguoiDungDTO registerLessor(NguoiDungDTO userDTO);

    NguoiDungDTO registerStaff(NguoiDungDTO userDTO);

    NguoiDungDTO registerAdmin(NguoiDungDTO userDTO);

    List<NguoiDungDTO> getNguoiDungsByMaVaiTro(String maVaiTro);

    NguoiDungResponseDTO getNguoiDungByMaNguoiDung(String maNguoiDung);

    NguoiDungDTO getNguoiDungByEmail(String name);

}
