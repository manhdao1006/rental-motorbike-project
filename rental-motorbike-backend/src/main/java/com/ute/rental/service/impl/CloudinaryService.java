package com.ute.rental.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(byte[] fileData) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "hop-dong-" + timestamp + ".docx";

            Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, ObjectUtils.asMap(
                    "resource_type", "raw",
                    "public_id", "hop-dong/" + fileName,
                    "folder", "file-hop-dong"));

            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
