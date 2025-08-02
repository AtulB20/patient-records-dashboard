package com.atulbh.security.service;

import com.atulbh.security.dao.User;
import com.healthcare.common.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TokenService {

  @Value("${jwt.secret}")
  private String secret;

  @Autowired
  private UserDetailsService userService;

  public String generateToken(String username) {
    User userDetails = (User) userService.loadUserByUsername(username);
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .subject(username)
        .claims(c-> {
          c.put("id", userDetails.getId());
          c.put("roles", roles);
        })
        .build();

    return Jwts.builder()
        .claims(claims.getClaims())
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(getSignKey(), Jwts.SIG.HS256)
        .compact();
  }

  public UserDTO getUserDTO() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
      Jwt jwt = (Jwt) authentication.getPrincipal();
      Map<String, Object> claims = jwt.getClaims();
      String username = jwt.getSubject();
      
      return UserDTO.builder()
          .id(Long.parseLong(claims.get("id").toString()))
          .username(username)
          .role(((List<String>) claims.get("roles")).get(0)) // Assuming single role for simplicity
          .build();
    }
    throw new IllegalStateException("Cannot get user DTO: no authentication or JWT found");
  }

  public String extractUserName(String token) {
    // Extract and return the subject claim from the token
    return extractClaim(token, Claims::getSubject);
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
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
