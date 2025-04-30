package com.biggest.bank_app.dto;

import java.math.BigDecimal;

public class CreditDebitDTO {

    private String accountNumber;

    private BigDecimal amount;


    public CreditDebitDTO(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public CreditDebitDTO() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
