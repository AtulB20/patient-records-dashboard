package com.healthcare.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
  private static final String BEARER_PREFIX = "Bearer ";
  private static final String X_USER_ID_HEADER = "X-User-ID";
  private static final String X_USER_ROLES_HEADER = "X-User-Role";

  private final SecurityServiceClient securityServiceClient;

  public AuthenticationFilter(SecurityServiceClient securityServiceClient) {
    this.securityServiceClient = securityServiceClient;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String authorizationHeader = request.getHeaders().getFirst(AUTHORIZATION);

    // Skip basic auth requests
    if (authorizationHeader != null && exchange.getRequest().getPath().toString().equals("/api/auth/token") && authorizationHeader.startsWith("Basic ")) {
      return chain.filter(exchange);
    }

    if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
      // No token or invalid format, reject unauthenticated access
      return this.onError(exchange, "Authorization header missing or invalid", HttpStatus.UNAUTHORIZED);
    }

    String token = authorizationHeader.substring(BEARER_PREFIX.length());

    return securityServiceClient.validateToken(token)
        .flatMap(authResponse -> {
          // Add userId and roles to request headers for downstream services
          ServerHttpRequest.Builder requestBuilder = request.mutate();

          // Add X-User-ID header
          requestBuilder.header(X_USER_ID_HEADER, String.valueOf(authResponse.getId()));

          // Add X-User-Role header
          requestBuilder.header(X_USER_ROLES_HEADER, authResponse.getRole());

          // Continue the filter chain with the modified request
          return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
        })
        .switchIfEmpty(this.onError(exchange, "Authentication failed by security service", HttpStatus.UNAUTHORIZED))
        .onErrorResume(e -> {
          // Catch any unexpected errors during filter processing (e.g., security service unavailable)
          System.err.println("Error during authentication filter: " + e.getMessage());
          return this.onError(exchange, "Authentication service unavailable or error", HttpStatus.INTERNAL_SERVER_ERROR);
        });
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
    log.error("Authentication error: {} for request: {}", errorMessage, exchange.getRequest().getURI());
    exchange.getResponse().setStatusCode(httpStatus);
    // Optionally, you can add a response body for more detail
    // byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
    // DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
    // return exchange.getResponse().writeWith(Mono.just(buffer));
    return exchange.getResponse().setComplete();
  }
}
