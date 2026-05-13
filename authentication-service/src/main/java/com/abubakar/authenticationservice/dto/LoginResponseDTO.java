package com.abubakar.authenticationservice.dto;

public record LoginResponseDTO(String jwt, boolean passwordNeedToBeUpdate) {
}
