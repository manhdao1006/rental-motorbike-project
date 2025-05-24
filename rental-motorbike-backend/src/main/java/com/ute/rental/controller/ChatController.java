package com.ute.rental.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ute.rental.dto.HoiThoaiDTO;
import com.ute.rental.dto.TinNhanDTO;
import com.ute.rental.entity.HoiThoaiEntity;
import com.ute.rental.entity.NguoiDungEntity;
import com.ute.rental.entity.TinNhanEntity;
import com.ute.rental.repository.HoiThoaiRepository;
import com.ute.rental.repository.NguoiDungRepository;
import com.ute.rental.repository.TinNhanRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final TinNhanRepository tinNhanRepository;
    private final HoiThoaiRepository hoiThoaiRepository;
    private final NguoiDungRepository nguoiDungRepository;

    @GetMapping("/chat/messages")
    public List<TinNhanDTO> getMessages(@RequestParam String maHoiThoai) {
        List<TinNhanEntity> tinNhanEntities = tinNhanRepository
                .findByHoiThoai_MaHoiThoaiOrderByThoiGianGuiAsc(maHoiThoai);

        return tinNhanEntities.stream().map(tn -> {
            TinNhanDTO tinNhanDTO = new TinNhanDTO();
            tinNhanDTO.setMaHoiThoai(maHoiThoai);
            tinNhanDTO.setMaNguoiDung(tn.getNguoiDung().getMaNguoiDung());
            tinNhanDTO.setNoiDung(tn.getNoiDung());
            tinNhanDTO.setThoiGianGui(tn.getThoiGianGui().toString());
            return tinNhanDTO;
        }).toList();
    }

    @GetMapping("/chat/conversations/khach-hang")
    public List<HoiThoaiDTO> getConversationsByMaKhachHang(@RequestParam String maKhachHang) {
        List<HoiThoaiEntity> hoiThoaiEntities = hoiThoaiRepository
                .findByKhachHang_MaNguoiDung(maKhachHang);

        return hoiThoaiEntities.stream().map(ht -> {
            HoiThoaiDTO hoiThoaiDTO = new HoiThoaiDTO();
            hoiThoaiDTO.setMaHoiThoai(ht.getMaHoiThoai());
            hoiThoaiDTO.setMaKhachHang(maKhachHang);
            hoiThoaiDTO.setMaChuCuaHang(ht.getChuCuaHang().getMaNguoiDung());

            List<TinNhanEntity> tinNhanEntities = tinNhanRepository
                    .findByHoiThoai_MaHoiThoaiOrderByThoiGianGuiAsc(ht.getMaHoiThoai());
            List<TinNhanDTO> tinNhanDTOs = tinNhanEntities.stream()
                    .map(entity -> {
                        TinNhanDTO dto = new TinNhanDTO();
                        dto.setMaHoiThoai(entity.getHoiThoai().getMaHoiThoai());
                        dto.setNoiDung(entity.getNoiDung());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        dto.setThoiGianGui(entity.getThoiGianGui().format(formatter));
                        return dto;
                    })
                    .collect(Collectors.toList());

            hoiThoaiDTO.setTinNhans(tinNhanDTOs);

            return hoiThoaiDTO;
        }).toList();
    }

    @GetMapping("/chat/conversations/chu-cua-hang")
    public List<HoiThoaiDTO> getConversationsByMaChuCuaHang(@RequestParam String maChuCuaHang) {
        List<HoiThoaiEntity> hoiThoaiEntities = hoiThoaiRepository
                .findByChuCuaHang_MaNguoiDung(maChuCuaHang);

        return hoiThoaiEntities.stream().map(ht -> {
            HoiThoaiDTO hoiThoaiDTO = new HoiThoaiDTO();
            hoiThoaiDTO.setMaHoiThoai(ht.getMaHoiThoai());
            hoiThoaiDTO.setMaChuCuaHang(maChuCuaHang);
            hoiThoaiDTO.setMaKhachHang(ht.getKhachHang().getMaNguoiDung());

            List<TinNhanEntity> tinNhanEntities = tinNhanRepository
                    .findByHoiThoai_MaHoiThoaiOrderByThoiGianGuiAsc(ht.getMaHoiThoai());
            List<TinNhanDTO> tinNhanDTOs = tinNhanEntities.stream()
                    .map(entity -> {
                        TinNhanDTO dto = new TinNhanDTO();
                        dto.setMaHoiThoai(entity.getHoiThoai().getMaHoiThoai());
                        dto.setNoiDung(entity.getNoiDung());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        dto.setThoiGianGui(entity.getThoiGianGui().format(formatter));
                        return dto;
                    })
                    .collect(Collectors.toList());

            hoiThoaiDTO.setTinNhans(tinNhanDTOs);

            return hoiThoaiDTO;
        }).toList();
    }

    @PostMapping("/chat/start")
    public String startConversation(@RequestParam String maKhachHang, @RequestParam String maChuCuaHang) {
        Optional<HoiThoaiEntity> existing = hoiThoaiRepository
                .findByKhachHang_MaNguoiDungAndChuCuaHang_MaNguoiDung(maKhachHang,
                        maChuCuaHang);
        if (existing.isPresent()) {
            return existing.get().getMaHoiThoai();
        }

        NguoiDungEntity customer = nguoiDungRepository.findOneByMaNguoiDung(maKhachHang).orElseThrow();
        NguoiDungEntity shopOwner = nguoiDungRepository.findOneByMaNguoiDung(maChuCuaHang).orElseThrow();

        HoiThoaiEntity hoiThoaiEntity = new HoiThoaiEntity();
        hoiThoaiEntity.setMaHoiThoai("CVS_" + maKhachHang + "_" + maChuCuaHang);
        hoiThoaiEntity.setKhachHang(customer);
        hoiThoaiEntity.setChuCuaHang(shopOwner);

        return hoiThoaiRepository.save(hoiThoaiEntity).getMaHoiThoai();
    }

    @MessageMapping("/chat/send")
    public void sendMessage(TinNhanDTO tinNhanDTO) {
        HoiThoaiEntity hoiThoaiEntity = hoiThoaiRepository.findOneByMaHoiThoai(tinNhanDTO.getMaHoiThoai())
                .orElseThrow();
        NguoiDungEntity nguoiDungEntity = nguoiDungRepository.findOneByMaNguoiDung(tinNhanDTO.getMaNguoiDung())
                .orElseThrow();

        TinNhanEntity tinNhanEntity = new TinNhanEntity();
        tinNhanEntity.setMaTinNhan(generateMaTinNhan());
        tinNhanEntity.setHoiThoai(hoiThoaiEntity);
        tinNhanEntity.setNguoiDung(nguoiDungEntity);
        tinNhanEntity.setNoiDung(tinNhanDTO.getNoiDung());
        tinNhanEntity.setThoiGianGui(LocalDateTime.now());

        tinNhanRepository.save(tinNhanEntity);

        messagingTemplate.convertAndSend("/topic/conversation/" + tinNhanDTO.getMaHoiThoai(), tinNhanDTO);
    }

    private String generateMaTinNhan() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int count = tinNhanRepository.countByMaTinNhanStartingWith("TN" + datePart) + 1;

        String stt = String.valueOf(count);

        return "TN" + datePart + stt;
    }

}
