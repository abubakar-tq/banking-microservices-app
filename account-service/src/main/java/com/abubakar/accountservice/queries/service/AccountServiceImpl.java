package com.abubakar.accountservice.queries.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import com.abubakar.accountservice.commands.util.generator.IdGenerator;
import com.abubakar.accountservice.common.enums.AccountStatus;
import com.abubakar.accountservice.common.enums.Currency;
import com.abubakar.accountservice.common.enums.OperationType;
import com.abubakar.accountservice.queries.dto.AccountResponseDTO;
import com.abubakar.accountservice.queries.dto.OperationResponseDTO;
import com.abubakar.accountservice.queries.entity.Account;
import com.abubakar.accountservice.queries.entity.Operation;
import com.abubakar.accountservice.queries.exception.AccountNotFoundException;
import com.abubakar.accountservice.queries.exception.OperationNotFoundException;
import com.abubakar.accountservice.commands.exception.AccountNotActivatedException;
import com.abubakar.accountservice.commands.exception.AmountNotSufficientException;
import com.abubakar.accountservice.commands.exception.BalanceNotSufficientException;
import com.abubakar.accountservice.commands.exception.NotAuthorizedException;
import com.abubakar.accountservice.queries.reposiory.AccountRepository;
import com.abubakar.accountservice.queries.reposiory.OperationRepository;
import com.abubakar.accountservice.queries.util.mapper.Mapper;
import com.abubakar.accountservice.queries.util.notification.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final NotificationService notificationService;
    private final IdGenerator idGenerator;

    public AccountServiceImpl(AccountRepository accountRepository,
                              OperationRepository operationRepository,
                              NotificationService notificationService,
                              IdGenerator idGenerator) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.notificationService = notificationService;
        this.idGenerator = idGenerator;
    }

    @Override
    public AccountResponseDTO createAccount(String customerId, String email, Currency currency, String createdBy) {
        log.info("Creating account for customer {}", customerId);
        LocalDateTime now = LocalDateTime.now();
        Account account = Account.builder()
                .id(idGenerator.autoGenerateId())
                .customerId(customerId)
                .email(email)
                .currency(currency)
                .status(AccountStatus.ACTIVATED)
                .balance(BigDecimal.ZERO)
                .createdBy(createdBy)
                .createdDate(now)
                .lastModifiedBy(createdBy)
                .lastModifiedDate(now)
                .build();
        Account saved = accountRepository.save(account);
        log.info("Account created with id {} for customer {}", saved.getId(), customerId);
        notificationService.sentAccountCreationNotification(saved.getId(), email, now);
        notificationService.sendAccountActivationNotification(email, now);
        return Mapper.fromAccount(saved);
    }

    @Override
    public AccountResponseDTO activateAccount(String accountId, String modifiedBy) {
        log.info("Activating account {}", accountId);
        Account account = findAccountById(accountId);
        LocalDateTime now = LocalDateTime.now();
        account.setStatus(AccountStatus.ACTIVATED);
        account.setLastModifiedBy(modifiedBy);
        account.setLastModifiedDate(now);
        Account saved = accountRepository.save(account);
        log.info("Account {} activated by {}", accountId, modifiedBy);
        notificationService.sendAccountActivationNotification(account.getEmail(), now);
        return Mapper.fromAccount(saved);
    }

    @Override
    public AccountResponseDTO suspendAccount(String accountId, String modifiedBy) {
        log.info("Suspending account {}", accountId);
        Account account = findAccountById(accountId);
        LocalDateTime now = LocalDateTime.now();
        account.setStatus(AccountStatus.SUSPENDED);
        account.setLastModifiedBy(modifiedBy);
        account.setLastModifiedDate(now);
        Account saved = accountRepository.save(account);
        log.info("Account {} suspended by {}", accountId, modifiedBy);
        notificationService.sendAccountSuspensionNotification(account.getEmail(), now);
        return Mapper.fromAccount(saved);
    }

    @Override
    public void deleteAccount(String accountId, String deletedBy) {
        log.info("Deleting account {}", accountId);
        Account account = findAccountById(accountId);
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new NotAuthorizedException("Cannot delete account with non-zero balance: " + account.getBalance());
        }
        String email = account.getEmail();
        operationRepository.deleteByAccountId(accountId);
        accountRepository.deleteById(accountId);
        log.info("Account {} deleted by {}", accountId, deletedBy);
        notificationService.sendAccountDeletedNotification(accountId, email, LocalDateTime.now());
    }

    @Override
    public OperationResponseDTO creditAccount(String accountId, BigDecimal amount, String description, String creditedBy) {
        log.info("Crediting account {} with amount {}", accountId, amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountNotSufficientException("Credit amount must be positive: " + amount);
        }
        Account account = findAccountById(accountId);
        if (!account.getStatus().equals(AccountStatus.ACTIVATED)) {
            throw new AccountNotActivatedException("Account not activated: " + account.getStatus());
        }
        LocalDateTime now = LocalDateTime.now();
        account.setBalance(account.getBalance().add(amount));
        account.setLastModifiedBy(creditedBy);
        account.setLastModifiedDate(now);
        Account saved = accountRepository.save(account);
        Operation operation = Operation.builder()
                .account(saved)
                .amount(amount)
                .type(OperationType.CREDIT)
                .description(description)
                .createdBy(creditedBy)
                .dateTime(now)
                .build();
        Operation savedOp = operationRepository.save(operation);
        log.info("Account {} credited with {}", accountId, amount);
        notificationService.sendAccountCreditedNotification(account.getEmail(), amount, saved.getBalance(), now);
        return Mapper.fromOperation(savedOp);
    }

    @Override
    public OperationResponseDTO debitAccount(String accountId, BigDecimal amount, String description, String debitedBy) {
        log.info("Debiting account {} with amount {}", accountId, amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountNotSufficientException("Debit amount must be positive: " + amount);
        }
        Account account = findAccountById(accountId);
        if (!account.getStatus().equals(AccountStatus.ACTIVATED)) {
            throw new AccountNotActivatedException("Account not activated: " + account.getStatus());
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BalanceNotSufficientException("Insufficient balance: " + account.getBalance());
        }
        LocalDateTime now = LocalDateTime.now();
        account.setBalance(account.getBalance().subtract(amount));
        account.setLastModifiedBy(debitedBy);
        account.setLastModifiedDate(now);
        Account saved = accountRepository.save(account);
        Operation operation = Operation.builder()
                .account(saved)
                .amount(amount)
                .type(OperationType.DEBIT)
                .description(description)
                .createdBy(debitedBy)
                .dateTime(now)
                .build();
        Operation savedOp = operationRepository.save(operation);
        log.info("Account {} debited with {}", accountId, amount);
        notificationService.sendAccountDebitedNotification(account.getEmail(), amount, saved.getBalance(), now);
        return Mapper.fromOperation(savedOp);
    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount, String description, String transferBy) {
        log.info("Transferring {} from {} to {}", amount, fromAccountId, toAccountId);
        debitAccount(fromAccountId, amount, description, transferBy);
        try {
            creditAccount(toAccountId, amount, description, transferBy);
        } catch (Exception e) {
            log.warn("Credit failed during transfer, reversing debit: {}", e.getMessage());
            creditAccount(fromAccountId, amount, "Reversal: " + description, transferBy);
            throw e;
        }
        log.info("Transfer of {} from {} to {} completed", amount, fromAccountId, toAccountId);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(String accountId) {
        log.info("Getting account by id {}", accountId);
        return Mapper.fromAccount(findAccountById(accountId));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByCustomerId(String customerId) {
        log.info("Getting account by customerId {}", customerId);
        Account account = accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for customer: " + customerId));
        return Mapper.fromAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResponseDTO getOperationById(String operationId) {
        log.info("Getting operation by id {}", operationId);
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new OperationNotFoundException("Operation not found: " + operationId));
        return Mapper.fromOperation(operation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationResponseDTO> getOperationsByAccountId(String accountId, int page, int size) {
        log.info("Getting operations for account {}", accountId);
        Page<Operation> operationPage = operationRepository.findByAccountId(accountId, PageRequest.of(page, size));
        return Mapper.fromOperations(operationPage.getContent());
    }

    private Account findAccountById(@NotNull String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + id));
    }
}
