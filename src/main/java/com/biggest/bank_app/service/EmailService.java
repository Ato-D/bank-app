package com.biggest.bank_app.service;

import com.biggest.bank_app.dto.EmailDetailsDTO;

public interface EmailService {

    void sendEmailAlert(EmailDetailsDTO emailDetailsDTO);
}
