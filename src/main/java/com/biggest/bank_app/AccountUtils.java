package com.biggest.bank_app;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@Data
@RequiredArgsConstructor
public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account";
    public static final String ACCOUNT_CREATION_SUCCESS = "201";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been created successfully";
    public static final String ACCOUNT_NOT_EXIST_CODE = "404";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";
    public static final String ACCOUNT_FOUND_CODE = "201";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "200";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account Credited Successfully";




    public static String generateAccountNumber() {

        Year currentYear = Year.now();

        int max = 999999;
        int min = 100000;

        int randomNumber = (int)(Math.random() * (max - min + 1)) + min;

        /**
         * Converts the current year and random number to string and concatenates them
         */

        String year = String.valueOf(currentYear);
        String randomNUmber = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(year)
                .append(randomNUmber);

        return accountNumber.toString();
    }
}
