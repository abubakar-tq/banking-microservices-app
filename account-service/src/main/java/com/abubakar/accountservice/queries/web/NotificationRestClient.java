package com.abubakar.accountservice.queries.web;

import jakarta.validation.Valid;
import com.abubakar.accountservice.queries.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATION-SERVICE")
public interface NotificationRestClient {

    @PostMapping("/bank/notifications/send")
    void sendNotification(@RequestBody @Valid NotificationRequestDTO notification);
}
