package com.healthcare.patient.util;

import com.healthcare.common.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Utility class for security-related operations.
 */
@Component
public class SecurityUtils {

    /**
     * Fetches the currently authenticated user's details.
     *
     * @return UserDTO containing the authenticated user's details
     * @throws IllegalStateException if no authenticated user is found or if the user cannot be loaded
     */
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        UserDTO.UserDTOBuilder builder = UserDTO.builder().username(authentication.getName());
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getToken().getClaims();
            if (claims.containsKey("id")) {
                builder.id((Long) claims.get("id"));
            }
        }

        return builder.build();
    }

    /**
     * Gets the username of the currently authenticated user.
     *
     * @return the username of the authenticated user
     * @throws IllegalStateException if no authenticated user is found
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication.getName();
    }
}
