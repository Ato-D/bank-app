package com.biggest.bank_app.controller;

import com.biggest.bank_app.dto.BankResponseDTO;
import com.biggest.bank_app.dto.CreditDebitDTO;
import com.biggest.bank_app.dto.EnquiryRequestDTO;
import com.biggest.bank_app.dto.UserRequestDTO;
import com.biggest.bank_app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private  UserService userService;
    private UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public BankResponseDTO createAccount(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createAccount(userRequestDTO);
    }

    @GetMapping("/balance-enquiry")
    public BankResponseDTO balanceEnquiry(@RequestParam String accountNumber) {
        EnquiryRequestDTO enquiryRequestDTO = new EnquiryRequestDTO();
        enquiryRequestDTO.setAccountNumber(accountNumber);
        return userService.balanceEnquiry(enquiryRequestDTO);
    }

    @GetMapping("/name-enquiry")
    public String nameEnquiry(@RequestParam String accountNumber) {
        EnquiryRequestDTO enquiryRequestDTO = new EnquiryRequestDTO();
        enquiryRequestDTO.setAccountNumber(accountNumber);
        return userService.nameEnquiry(enquiryRequestDTO);
    }

    @PostMapping("/credit-account")
    public BankResponseDTO creditAccount(@RequestBody CreditDebitDTO creditDebitDTO) {
        return userService.creditAccount(creditDebitDTO);
    }
}
