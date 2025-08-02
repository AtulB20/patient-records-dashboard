package com.healthcare.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.healthcare.common.dto.UserDTO;

import reactor.core.publisher.Mono;

@Component
public class SecurityServiceClient {

  private final WebClient webClient;

  public SecurityServiceClient(@Value("${auth-service.url}") String authServiceUrl) {
    this.webClient = WebClient.builder()
        .baseUrl(authServiceUrl)
        .build();
  }

  public Mono<UserDTO> validateToken(String token) {
    return webClient.get()
        .uri("/auth/validate")
        .header("Authorization", "Bearer " + token)
        .retrieve()
        .bodyToMono(UserDTO.class);
  }
}
