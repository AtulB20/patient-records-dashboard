package com.healthcare.security.service;

import com.healthcare.common.dto.UserDTO;
import com.healthcare.security.model.Role;
import com.healthcare.security.model.User;
import com.healthcare.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getLoggedInUser() {
    String username = "";

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      username = authentication.getName();
    }
    try {
      Optional<User> optionalUser = userRepository.findByUsername(username);
      return optionalUser.orElse(null);
    } catch (Exception e) {
      log.error("Error fetching loggedIn user", e);
    }
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(username);
      return optionalUser.orElse(null);
    } catch (Exception e) {
      log.error("Error fetching loggedIn user", e);
    }
    return null;
  }

  public UserDTO getUserDTO(String username) {
    User userDetails = (User) loadUserByUsername(username);
    Set<String> roles = userDetails.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());
    return UserDTO.builder()
        .username(userDetails.getUsername())
        .id(userDetails.getId())
        .role(roles)
        .build();
  }
}
