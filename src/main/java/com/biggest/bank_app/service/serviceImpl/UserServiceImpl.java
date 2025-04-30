package com.biggest.bank_app.service.serviceImpl;

import com.biggest.bank_app.AccountUtils;
import com.biggest.bank_app.dto.*;
import com.biggest.bank_app.entity.User;
import com.biggest.bank_app.repository.UserRepository;
import com.biggest.bank_app.service.EmailService;
import com.biggest.bank_app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    @Override
    public BankResponseDTO createAccount(UserRequestDTO userRequestDTO) {

        /**
         * Check if a user already exists before creating a new account
         * Create a new user and save to the database
         */

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            BankResponseDTO response = new BankResponseDTO();
            response.setResponseCode(AccountUtils.ACCOUNT_EXISTS_CODE);
            response.setResponseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE);
            response.setAccountInfo(null);
            return response;
        }

/*        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

*/

        User newUser = new User();
        newUser.setFirstName(userRequestDTO.getFirstName());
        newUser.setLastName(userRequestDTO.getLastName());
        newUser.setOtherName(userRequestDTO.getOtherName());
        newUser.setGender(userRequestDTO.getGender());
        newUser.setAddress(userRequestDTO.getAddress());
        newUser.setStateOfOrigin(userRequestDTO.getStateOfOrigin());
        newUser.setAccountNumber(AccountUtils.generateAccountNumber());
        newUser.setAccountBalance(BigDecimal.ZERO);
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setPhoneNumber(userRequestDTO.getPhoneNumber());
        newUser.setAlternativePhoneNumber(userRequestDTO.getAlternativePhoneNumber());
        newUser.setStatus("ACTIVE");

//        return newUser;


        User savedUser = userRepository.save(newUser);

        /**
         * Send email alert
         */
        EmailDetailsDTO emailDetailsDTO = new EmailDetailsDTO();
        emailDetailsDTO.setRecipient(savedUser.getEmail());
        emailDetailsDTO.setSubject("ACCOUNT CREATION");
        emailDetailsDTO.setMessageBody("Congratulations! Your Account Has Been Successfully Created.\nYour Account Details: \n" +
                "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName()
                + "\nAccount Number: " + savedUser.getAccountNumber());

        emailService.sendEmailAlert(emailDetailsDTO);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber(savedUser.getAccountNumber());
        accountInfo.setAccountBalance(savedUser.getAccountBalance());
        accountInfo.setAccountName(String.join("-", savedUser.getFirstName(), savedUser.getLastName(), savedUser.getOtherName()));

        BankResponseDTO response = new BankResponseDTO();
        response.setResponseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS);
        response.setResponseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE);
        response.setAccountInfo(accountInfo);

        return response;



//        log.debug("Email successfully sent to {}", savedUser.getEmail());
/*        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(savedUser.getAccountNumber())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(String.join("-",savedUser.getFirstName(), savedUser.getLastName(),savedUser.getOtherName()))
                        .build())

                .build();*/
    }

    @Override
    public BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO) {

        /**
         * Check if the provided account number exists
         */

        boolean isAccountExist = userRepository.existsByAccountNumber(enquiryRequestDTO.getAccountNumber());

        if (!isAccountExist) {

            BankResponseDTO response = new BankResponseDTO();
            response.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
            response.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
            response.setAccountInfo(null);

            return response;

        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequestDTO.getAccountNumber());

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber(enquiryRequestDTO.getAccountNumber());
        accountInfo.setAccountBalance(foundUser.getAccountBalance());
        accountInfo.setAccountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName());

        BankResponseDTO response = new BankResponseDTO();
        response.setResponseCode(AccountUtils.ACCOUNT_FOUND_CODE);
        response.setResponseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS);
        response.setAccountInfo(accountInfo);

        return response;
    }


    @Override
    public String nameEnquiry(EnquiryRequestDTO enquiryRequestDTO) {
        boolean isAccountExist = userRepository.existsByAccountNumber(enquiryRequestDTO.getAccountNumber());

        if (!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;

        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequestDTO.getAccountNumber());

        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();

    }

    @Override
    public BankResponseDTO creditAccount(CreditDebitDTO creditDebitDTO) {

        /**
         * check if the account number exists
         * if it does, we then deposit
         */

        boolean isAccountExist = userRepository.existsByAccountNumber(creditDebitDTO.getAccountNumber());
        if (!isAccountExist) {
            BankResponseDTO bankResponseDTO = new BankResponseDTO();
            bankResponseDTO.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
            bankResponseDTO.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
            bankResponseDTO.setAccountInfo(null);

            return bankResponseDTO;
        }
        else {

            User userToCredit = userRepository.findByAccountNumber(creditDebitDTO.getAccountNumber());
            userToCredit.setAccountBalance(creditDebitDTO.getAmount().add(userToCredit.getAccountBalance()));

            userRepository.save(userToCredit);

            /**
             * Send email alert
             */
            EmailDetailsDTO emailDetailsDTO = new EmailDetailsDTO();
            emailDetailsDTO.setRecipient(userToCredit.getEmail());
            emailDetailsDTO.setSubject("ACCOUNT CREDITED");
            emailDetailsDTO.setMessageBody("Dear " + userToCredit.getFirstName() + ", An amount of " + creditDebitDTO.getAmount() + " has been credited to your account. Your New Balance: GHS " +
                    userToCredit.getAccountBalance() + "\nThank You");

            emailService.sendEmailAlert(emailDetailsDTO);

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountNumber(creditDebitDTO.getAccountNumber());
            accountInfo.setAccountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName());
            accountInfo.setAccountBalance(userToCredit.getAccountBalance());

            BankResponseDTO bankResponseDTO = new BankResponseDTO();
            bankResponseDTO.setResponseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS);
            bankResponseDTO.setResponseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE);
            bankResponseDTO.setAccountInfo(accountInfo);

            return  bankResponseDTO;

        }
    }
}
