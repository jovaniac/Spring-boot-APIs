package com.demo.springbootapis.controller;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.springbootapis.model.security.AuthProvider;
import com.demo.springbootapis.model.security.Role;
import com.demo.springbootapis.model.security.RoleName;
import com.demo.springbootapis.model.security.User;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.repository.RoleRepository;
import com.demo.springbootapis.security.CurrentUser;
import com.demo.springbootapis.security.oauth2.CustomUserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {

  @NonNull
  CustomUserDetailsService userService;

  @NonNull
  PasswordEncoder passwordEncoder;

  @NonNull
  RoleRepository roleRepository;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse> signup(@RequestBody User user) {
    // User already exist
    if (userService.existsByUsername(user.getUsername())) {
      return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
        user.getEmail(), AuthProvider.local);

    // Default user role to be GUEST
    Role userRole = roleRepository.findByName(RoleName.ROLE_GUEST)
        .orElseThrow(() -> new AppException("User Role not set."));
    newUser.setRoles(Collections.singletonList(userRole));

    User result = userService.save(user);

    return new ResponseEntity(new ApiResponse(true, "User registered successfully"),
        HttpStatus.CREATED);
  }

  @GetMapping("/currentLoginUser")
  public ResponseEntity<UserPrincipal> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    return new ResponseEntity(currentUser, HttpStatus.OK);
  }

//  @GetMapping("/user/me")
//  @PreAuthorize("hasRole('USER')")
//  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
//    return userRepository.findById(userPrincipal.getId())
//        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
//  }
}
