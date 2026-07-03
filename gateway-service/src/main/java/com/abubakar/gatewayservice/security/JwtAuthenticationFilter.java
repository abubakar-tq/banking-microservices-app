package com.abubakar.gatewayservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Edge authentication for the whole platform.
 *
 * A GlobalFilter runs for EVERY route the gateway proxies. It rejects requests
 * with a missing / malformed / tampered / expired JWT with 401 BEFORE they reach
 * any downstream service. Valid requests continue with the Authorization header
 * intact, so each service still re-validates the token (defense-in-depth).
 *
 * Public paths (login, password reset, actuator, swagger) are allowed through
 * un-authenticated. CORS pre-flight (HTTP OPTIONS) is also allowed through so the
 * browser can complete its check.
 */
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final String BEARER = "Bearer ";

    /**
     * Ant-style patterns for endpoints that do NOT require a token.
     * Gateway paths look like /{SERVICE-NAME}/{context-path}/... e.g.
     * /AUTHENTICATION-SERVICE/bank/authentication/login — so we match on the
     * trailing segments with a leading /** to stay independent of the service name.
     */
    private static final List<String> OPEN_PATHS = List.of(
            "/**/authentication/**",   // login
            "/**/passwords/**",        // password reset flow
            "/**/actuator/**",         // health / metrics
            "/**/v3/api-docs/**",
            "/**/swagger-ui/**",
            "/**/swagger-ui.html"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ApplicationProperties properties;

    public JwtAuthenticationFilter(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Let CORS pre-flight and public endpoints through untouched.
        if (request.getMethod() == HttpMethod.OPTIONS || isOpen(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER)) {
            return unauthorized(exchange);
        }

        try {
            JWT.require(Algorithm.HMAC256(properties.getJwtSecret()))
                    .build()
                    .verify(header.substring(BEARER.length()));   // throws if bad signature or expired
        } catch (JWTVerificationException e) {
            return unauthorized(exchange);
        }

        return chain.filter(exchange);
    }

    private boolean isOpen(String path) {
        return OPEN_PATHS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    /**
     * Run early, before the routing filter forwards the request downstream.
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
