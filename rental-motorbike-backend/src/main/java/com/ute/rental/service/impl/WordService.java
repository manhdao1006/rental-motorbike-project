package com.ute.rental.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordService {

    private final CloudinaryService cloudinaryService;

    @SuppressWarnings("unchecked")
    public String generateAndUploadWord(Map<String, Object> formData) {
        Path tempFilePath = null;

        try {
            InputStream wordTemplate = new ClassPathResource("templates/hop-dong-cho-thue-xe-may.docx")
                    .getInputStream();
            XWPFDocument document = new XWPFDocument(wordTemplate);

            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
            String ngayHienTai = now.format(formatter);

            formatter = DateTimeFormatter.ofPattern("MM");
            String thangHienTai = now.format(formatter);

            formatter = DateTimeFormatter.ofPattern("yyyy");
            String namHienTai = now.format(formatter);

            List<Map<String, String>> danhSachXe = (List<Map<String, String>>) formData.get("danhSachXe");
            int tongSoLuongThue = danhSachXe.size();

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replaceText(paragraph, "{ngayHienTai}", ngayHienTai);
                replaceText(paragraph, "{thangHienTai}", thangHienTai);
                replaceText(paragraph, "{namHienTai}", namHienTai);

                replaceText(paragraph, "{tenChuCuaHang}", formData.getOrDefault("tenChuCuaHang", "").toString());
                replaceText(paragraph, "{ngaySinhChuCuaHang}",
                        formData.getOrDefault("ngaySinhChuCuaHang", "").toString());
                replaceText(paragraph, "{soCCCDChuCuaHang}", formData.getOrDefault("soCCCDChuCuaHang", "").toString());
                replaceText(paragraph, "{diaChiChuCuaHang}", formData.getOrDefault("diaChiChuCuaHang", "").toString());
                replaceText(paragraph, "{phuongXaChuCuaHang}",
                        formData.getOrDefault("phuongXaChuCuaHang", "").toString());
                replaceText(paragraph, "{quanHuyenChuCuaHang}",
                        formData.getOrDefault("quanHuyenChuCuaHang", "").toString());
                replaceText(paragraph, "{soDienThoaiChuCuaHang}",
                        formData.getOrDefault("soDienThoaiChuCuaHang", "").toString());

                replaceText(paragraph, "{tenCuaHang}", formData.getOrDefault("tenCuaHang", "").toString());
                replaceText(paragraph, "{diaChiCuaHang}", formData.getOrDefault("diaChiCuaHang", "").toString());
                replaceText(paragraph, "{soDienThoaiCuaHang}",
                        formData.getOrDefault("soDienThoaiCuaHang", "").toString());

                replaceText(paragraph, "{tenKhachHang}", formData.getOrDefault("tenKhachHang", "").toString());
                replaceText(paragraph, "{ngaySinhKhachHang}",
                        formData.getOrDefault("ngaySinhKhachHang", "").toString());
                replaceText(paragraph, "{soCCCDKhachHang}", formData.getOrDefault("soCCCDKhachHang", "").toString());
                replaceText(paragraph, "{diaChiKhachHang}", formData.getOrDefault("diaChiKhachHang", "").toString());
                replaceText(paragraph, "{soDienThoaiKhachHang}",
                        formData.getOrDefault("soDienThoaiKhachHang", "").toString());

                replaceText(paragraph, "{tongSoLuongThue}", tongSoLuongThue + "");
                replaceText(paragraph, "{lyDoThueXe}", formData.getOrDefault("lyDoThueXe", "").toString());
                replaceText(paragraph, "{tongGiaThue}", formData.getOrDefault("tongGiaThue", "").toString());
                replaceText(paragraph, "{phuongThucThanhToan}",
                        formData.getOrDefault("phuongThucThanhToan", "").toString());
            }

            for (XWPFTable table : document.getTables()) {
                fillTable(table, danhSachXe);
            }

            tempFilePath = Files.createTempFile("hop-dong-thue-xe-", ".docx");
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFilePath.toFile())) {
                document.write(fileOutputStream);
            }
            document.close();

            byte[] fileBytes = Files.readAllBytes(tempFilePath);
            return cloudinaryService.uploadFile(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (tempFilePath != null)
                    Files.deleteIfExists(tempFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void replaceText(XWPFParagraph paragraph, String placeholder, String value) {
        StringBuilder fullText = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String updatedText = fullText.toString().replace(placeholder, value);

        if (!fullText.toString().equals(updatedText)) {
            for (int i = runs.size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedText);
            newRun.setFontFamily("Times New Roman");
            newRun.setFontSize(12);
        }
    }

    private void fillTable(XWPFTable table, List<Map<String, String>> danhSachXe) {
        XWPFTableRow templateRow = null;
        for (XWPFTableRow row : table.getRows()) {
            if (row.getCell(0).getText().contains("{stt}")) {
                templateRow = row;
                break;
            }
        }

        if (templateRow != null) {
            table.removeRow(table.getRows().indexOf(templateRow));

            for (int i = 0; i < danhSachXe.size(); i++) {
                Map<String, String> xe = danhSachXe.get(i);
                XWPFTableRow newRow = table.createRow();

                setCellText(newRow.getCell(0), String.valueOf(i + 1));
                setCellText(newRow.getCell(1),
                        xe.get("loaiXe") + " " + xe.get("tenXe") + " " + xe.get("danhMucXe") + " Màu "
                                + xe.get("mauXe"));
                setCellText(newRow.getCell(2), xe.get("bienSoXe"));
                setCellText(newRow.getCell(3), xe.get("soMay"));
                setCellText(newRow.getCell(4), xe.get("soKhung"));
                setCellText(newRow.getCell(5), String.valueOf(xe.get("giaThue")));
                setCellText(newRow.getCell(6), xe.get("tuNgay"));
                setCellText(newRow.getCell(7), xe.get("denNgay"));
            }
        }
    }

    private void setCellText(XWPFTableCell cell, String text) {
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontFamily("Times New Roman");
        run.setFontSize(12);
    }

}
