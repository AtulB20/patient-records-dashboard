package com.atulbh.security.controller;

import com.atulbh.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final TokenService tokenService;

  public AuthController(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping("/token")
  public String token(Authentication authentication) {
    log.debug("Token requested for user: '{}'", authentication.getName());
    String token = tokenService.generateToken(authentication.getName());
    log.debug("Token generated {}", token);
    return token;
  }

  @GetMapping("/validate")
  public ResponseEntity<?> validateToken() {
    try {
      return ResponseEntity.ok(tokenService.getUserDTO());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
