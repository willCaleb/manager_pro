package com.project.pro.service.impl;

import com.project.pro.model.entity.SendEmail;
import com.project.pro.repository.SendEmailRepository;
import com.project.pro.service.IEmailService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final SendEmailRepository sendEmailRepository;



    @Override
    public void sendEmail(SendEmail sendEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(sendEmail.getTo());
            message.setSubject(sendEmail.getSubject());
            message.setText(sendEmail.getText());
            message.setFrom(sendEmail.getFrom());

            resolverCcAndBcc(sendEmail, message);

            message.setSentDate(DateUtils.getDate());
            javaMailSender.send(message);

            sendEmailRepository.save(sendEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resolverCcAndBcc(SendEmail sendEmail, SimpleMailMessage message) {
        if (Utils.isNotEmpty(sendEmail.getCc())) {
            message.setCc(sendEmail.getCc());
        }
        if (Utils.isNotEmpty(sendEmail.getBcc())) {
            message.setBcc(sendEmail.getBcc());
        }
    }

}
