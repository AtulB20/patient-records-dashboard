/*
package com.healthcare.common.service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SecurityService {

  @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
  private String secret;

  public boolean hasAnyRole(String... roles) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getAuthorities().stream()
        .anyMatch(a -> Arrays.asList(roles).contains(a.getAuthority()));
  }

  */
/*public boolean canUpdatePatient(Long patientId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    // Add custom logic, e.g., doctors can only update their own patients
    return hasAnyRole("ROLE_ADMIN") ||
        (hasAnyRole("ROLE_DOCTOR") && isAssignedToPatient(patientId, auth));
  }*//*

}
*/
