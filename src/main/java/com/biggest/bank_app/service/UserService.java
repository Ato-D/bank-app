package com.biggest.bank_app.service;

import com.biggest.bank_app.dto.BankResponseDTO;
import com.biggest.bank_app.dto.UserRequestDTO;

public interface UserService {

    BankResponseDTO createAccount(UserRequestDTO userRequestDTO);
}
