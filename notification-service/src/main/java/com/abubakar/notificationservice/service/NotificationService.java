package com.abubakar.notificationservice.service;

import com.abubakar.notificationservice.dto.NotificationRequestDTO;

public interface NotificationService {

    void send(NotificationRequestDTO dto);
}
