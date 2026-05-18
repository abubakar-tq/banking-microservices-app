package com.abubakar.accountservice.queries.service;

import com.abubakar.accountservice.common.enums.Currency;
import com.abubakar.accountservice.queries.dto.AccountResponseDTO;
import com.abubakar.accountservice.queries.dto.OperationResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponseDTO createAccount(String customerId, String email, Currency currency, String createdBy);

    AccountResponseDTO activateAccount(String accountId, String modifiedBy);

    AccountResponseDTO suspendAccount(String accountId, String modifiedBy);

    void deleteAccount(String accountId, String deletedBy);

    OperationResponseDTO creditAccount(String accountId, BigDecimal amount, String description, String creditedBy);

    OperationResponseDTO debitAccount(String accountId, BigDecimal amount, String description, String debitedBy);

    void transfer(String fromAccountId, String toAccountId, BigDecimal amount, String description, String transferBy);

    AccountResponseDTO getAccountById(String accountId);

    AccountResponseDTO getAccountByCustomerId(String customerId);

    OperationResponseDTO getOperationById(String operationId);

    List<OperationResponseDTO> getOperationsByAccountId(String accountId, int page, int size);
}
