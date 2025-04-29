package com.biggest.bank_app.service.serviceImpl;

import com.biggest.bank_app.AccountUtils;
import com.biggest.bank_app.dto.AccountInfo;
import com.biggest.bank_app.dto.BankResponseDTO;
import com.biggest.bank_app.dto.EmailDetailsDTO;
import com.biggest.bank_app.dto.UserRequestDTO;
import com.biggest.bank_app.entity.User;
import com.biggest.bank_app.repository.UserRepository;
import com.biggest.bank_app.service.EmailService;
import com.biggest.bank_app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


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
}
