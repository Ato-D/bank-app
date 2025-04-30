package com.biggest.bank_app.service;

import com.biggest.bank_app.dto.BankResponseDTO;
import com.biggest.bank_app.dto.CreditDebitDTO;
import com.biggest.bank_app.dto.EnquiryRequestDTO;
import com.biggest.bank_app.dto.UserRequestDTO;

public interface UserService {

    BankResponseDTO createAccount(UserRequestDTO userRequestDTO);

    BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO);

    String nameEnquiry(EnquiryRequestDTO enquiryRequestDTO);

    BankResponseDTO creditAccount(CreditDebitDTO creditDebitDTO);
}
