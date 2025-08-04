package com.healthcare.security.config;

import com.healthcare.common.dto.UserDTO;
import com.healthcare.common.service.TokenService;
import com.healthcare.security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Extracting token from the request header
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String userName = null;

    if (authHeader != null && authHeader.startsWith("Basic ")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      // Extracting the token from the Authorization header
      token = authHeader.substring(7);
      // Extracting username from the token
      userName = tokenService.extractUserName(token);
    } else if (request.getRequestURI().startsWith("/actuator")) {
      // Continue
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
      return;
    }

    // If username is extracted and there is no authentication in the current SecurityContext
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      // Loading UserDetails by username extracted from the token
      UserDTO userDetails = userService.getUserDTO(userName);

      // Validating the token with loaded UserDetails
      if (tokenService.validateToken(token, userDetails)) {

        List<SimpleGrantedAuthority> grantedAuthorities = userDetails.getRole().stream().map(SimpleGrantedAuthority::new).toList();

        // Creating an authentication token using UserDetails
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
        // Setting authentication details
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Setting the authentication token in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        return;
      }
    }

    // Proceeding with the filter chain
    filterChain.doFilter(request, response);
  }
}