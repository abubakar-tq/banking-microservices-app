package com.abubakar.accountservice.queries.web;

import com.abubakar.accountservice.queries.dto.AccountResponseDTO;
import com.abubakar.accountservice.queries.dto.OperationResponseDTO;
import com.abubakar.accountservice.queries.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/queries")
public class AccountQueryRestController {

    private final AccountService accountService;

    public AccountQueryRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/get-account/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/find-account/{customerId}")
    public ResponseEntity<AccountResponseDTO> getAccountByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(customerId));
    }

    @GetMapping("/get-operation/{id}")
    public ResponseEntity<OperationResponseDTO> getOperationById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getOperationById(id));
    }

    @GetMapping("/all-operations")
    public ResponseEntity<List<OperationResponseDTO>> getOperationsByAccountId(
            @RequestParam(name = "accountId") String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "9") int size) {
        return ResponseEntity.ok(accountService.getOperationsByAccountId(accountId, page, size));
    }
}
