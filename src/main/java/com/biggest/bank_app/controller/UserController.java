package com.biggest.bank_app.controller;

import com.biggest.bank_app.dto.BankResponseDTO;
import com.biggest.bank_app.dto.UserRequestDTO;
import com.biggest.bank_app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public BankResponseDTO createAccount(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createAccount(userRequestDTO);
    }
}
