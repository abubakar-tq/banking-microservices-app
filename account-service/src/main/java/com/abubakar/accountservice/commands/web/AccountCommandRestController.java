package com.abubakar.accountservice.commands.web;

import jakarta.validation.Valid;
import com.abubakar.accountservice.commands.dto.*;
import com.abubakar.accountservice.commands.exception.CustomerNotFoundException;
import com.abubakar.accountservice.common.enums.AccountStatus;
import com.abubakar.accountservice.common.security.SecurityInformation;
import com.abubakar.accountservice.queries.dto.AccountResponseDTO;
import com.abubakar.accountservice.queries.dto.OperationResponseDTO;
import com.abubakar.accountservice.queries.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/commands")
public class AccountCommandRestController {

    private final AccountService accountService;
    private final CustomerRestClient customerRestClient;
    private final SecurityInformation securityInformation;

    public AccountCommandRestController(AccountService accountService,
                                        CustomerRestClient customerRestClient,
                                        SecurityInformation securityInformation) {
        this.accountService = accountService;
        this.customerRestClient = customerRestClient;
        this.securityInformation = securityInformation;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> create(@RequestBody @Valid AccountRequestDTO dto) {
        CustomerExistResponseDTO customer = checkCustomerExist(dto.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found: " + dto.customerId());
        }
        AccountResponseDTO response = accountService.createAccount(
                customer.id(), customer.email(), dto.currency(), securityInformation.getUsername()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountResponseDTO> update(@RequestBody @Valid UpdateStatusRequestDTO dto) {
        String username = securityInformation.getUsername();
        AccountResponseDTO response;
        if (dto.status().equals(AccountStatus.ACTIVATED) || dto.status().equals(AccountStatus.CREATED)) {
            response = accountService.activateAccount(dto.accountId(), username);
        } else if (dto.status().equals(AccountStatus.SUSPENDED)) {
            response = accountService.suspendAccount(dto.accountId(), username);
        } else {
            throw new IllegalArgumentException("Unsupported status: " + dto.status());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit")
    public ResponseEntity<OperationResponseDTO> credit(@RequestBody @Valid OperationRequestDTO dto) {
        OperationResponseDTO response = accountService.creditAccount(
                dto.accountId(), dto.amount(), dto.description(), securityInformation.getUsername()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<OperationResponseDTO> debit(@RequestBody @Valid OperationRequestDTO dto) {
        OperationResponseDTO response = accountService.debitAccount(
                dto.accountId(), dto.amount(), dto.description(), securityInformation.getUsername()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequestDTO dto) {
        accountService.transfer(
                dto.accountIdFrom(), dto.accountIdTo(), dto.amount(), dto.description(), securityInformation.getUsername()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accountService.deleteAccount(id, securityInformation.getUsername());
        return ResponseEntity.noContent().build();
    }

    private CustomerExistResponseDTO checkCustomerExist(String customerId) {
        try {
            return customerRestClient.checkCustomerExist(customerId);
        } catch (Exception e) {
            return null;
        }
    }
}
