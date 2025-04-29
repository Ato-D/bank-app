package com.biggest.bank_app.service.serviceImpl;

import com.biggest.bank_app.dto.EmailDetailsDTO;
import com.biggest.bank_app.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendEmailAlert(EmailDetailsDTO emailDetailsDTO) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetailsDTO.getRecipient());
            simpleMailMessage.setText(emailDetailsDTO.getMessageBody());
            simpleMailMessage.setSubject(emailDetailsDTO.getSubject());

            javaMailSender.send(simpleMailMessage);
//            log.debug("Mail sent successfully");

        } catch (MailException e) {
            throw new RuntimeException(e);
        }

    }
}
