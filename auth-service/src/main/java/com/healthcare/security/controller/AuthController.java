package com.healthcare.security.controller;

import com.healthcare.common.dto.UserDTO;
import com.healthcare.common.service.TokenService;
import com.healthcare.security.service.UserService;
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
  private final UserService userService;

  public AuthController(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @PostMapping("/token")
  public String token(Authentication authentication) {
    log.debug("Token requested for user: '{}'", authentication.getName());

    UserDTO userDTO = userService.getUserDTO(authentication.getName());

    String token = tokenService.generateToken(userDTO);
    log.debug("Token generated {}", token);
    return token;
  }

  @GetMapping("/validate")
  public ResponseEntity<?> validateToken(Authentication authentication) {
    try {
      UserDTO userDTO = userService.getUserDTO(authentication.getName());
      return ResponseEntity.ok(userDTO);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
