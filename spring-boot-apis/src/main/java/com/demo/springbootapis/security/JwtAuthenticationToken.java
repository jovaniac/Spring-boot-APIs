package com.demo.springbootapis.security;

import com.demo.springbootapis.model.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationToken {
  private String token;
  private UserPrincipal user;
}
