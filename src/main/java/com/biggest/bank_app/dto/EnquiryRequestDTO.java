package com.biggest.bank_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnquiryRequestDTO {

    public String accountNumber;

    @Override
    public String toString() {
        return "EnquiryRequestDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
