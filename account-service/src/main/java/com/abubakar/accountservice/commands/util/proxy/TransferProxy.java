package com.abubakar.accountservice.commands.util.proxy;

import lombok.extern.slf4j.Slf4j;
import com.abubakar.accountservice.commands.dto.TransferRequestDTO;
import com.abubakar.accountservice.common.security.SecurityInformation;
import com.abubakar.accountservice.queries.service.AccountService;

@Slf4j
public class TransferProxy {

    public TransferProxy() {
        super();
    }

    public void transfer(TransferRequestDTO dto, AccountService accountService, SecurityInformation securityInformation) {
        log.info("Transfer request: {}", dto);
        accountService.transfer(
                dto.accountIdFrom(),
                dto.accountIdTo(),
                dto.amount(),
                dto.description(),
                securityInformation.getUsername()
        );
        log.info("Transfer completed successfully");
    }
}
