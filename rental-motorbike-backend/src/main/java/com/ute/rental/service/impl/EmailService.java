package com.ute.rental.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ute.rental.service.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailNguoiGui;

    @Override
    public void sendEmail(String nguoiNhan, String noiDung, String tieuDe) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress(emailNguoiGui));
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(nguoiNhan));
            mimeMessage.setText(noiDung, "UTF-8");
            mimeMessage.setSubject(tieuDe, "UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

}
