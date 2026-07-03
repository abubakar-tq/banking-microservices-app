package com.abubakar.gatewayservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Binds the JWT secret from application.yml (application.security.jwt.secret).
 * The gateway only needs the secret to VERIFY signatures; it never issues tokens.
 * (Plain getter instead of Lombok: gateway-service does not depend on Lombok.)
 */
@Component
public class ApplicationProperties {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }
}
