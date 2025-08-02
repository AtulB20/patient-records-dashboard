package com.atulbh.security.dao;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "HC_ROLE")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;  // e.g., "ROLE_ADMIN"

  @Override
  public String getAuthority() {
    return name;
  }
}
