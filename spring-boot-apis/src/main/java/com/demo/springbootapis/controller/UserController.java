package com.demo.springbootapis.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springbootapis.model.security.Role;
import com.demo.springbootapis.model.security.RoleName;
import com.demo.springbootapis.model.security.User;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.repository.RoleRepository;
import com.demo.springbootapis.security.CurrentUser;
import com.demo.springbootapis.service.CustomUserDetailsService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	CustomUserDetailsService userService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody User user) {
		//User already exist
		if(userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
		
		// Creating user's account
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        
        //Default user role to be GUEST
        Role userRole = roleRepository.findByName(RoleName.ROLE_GUEST)
                .orElseThrow(() -> new AppException("User Role not set."));
        newUser.setRoles(Collections.singletonList(userRole));
        
        User result = userService.save(user);
        
		return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
	}

	@GetMapping("/currentLoginUser")
	public ResponseEntity<UserPrincipal> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		return new ResponseEntity(currentUser, HttpStatus.OK);
	}
}
