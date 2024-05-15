package com.project.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/sendEmail")
    public String sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("matheushdpg@gmail.com");
        message.setSubject("¡Hola!");
        message.setText("Este es un correo de prueba enviado desde mi aplicación Spring Boot.");
        javaMailSender.send(message);
        return "Correo enviado exitosamente.";
    }
}