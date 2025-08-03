package com.healthcare.common.service;

import com.healthcare.common.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
public class TokenService {

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(UserDTO userDTO) {

    Map<String, Object> customClaims = new HashMap<>();
    customClaims.put("id", userDTO.getId());
    customClaims.put("roles", userDTO.getRole());

    return Jwts.builder()
        .claims(customClaims)
        .subject(userDTO.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(getSignKey(), Jwts.SIG.HS256)
        .compact();
  }

  public String extractUserName(String token) {
    // Extract and return the subject claim from the token
    return extractClaim(token, Claims::getSubject);
  }

  public Set<String> extractRoles(String token) {
    // Extract and return the subject claim from the token
    Claims claims = extractAllClaims(token);
    return (Set<String> ) claims.get("roles", Set.class);
  }

  public Boolean validateToken(String token, UserDTO userDetails) {
    // Extract username from token and check if it matches UserDetails' username
    final String userName = extractUserName(token);
    // Also check if the token is expired
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public Boolean isTokenExpired(String token) {
    // Check if the token's expiration time is before the current time
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    // Extract and return the expiration claim from the token
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    // Extract the specified claim using the provided function
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    // Parse and return all claims from the token
    return Jwts.parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
