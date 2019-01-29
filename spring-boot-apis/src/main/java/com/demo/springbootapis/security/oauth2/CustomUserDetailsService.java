package com.demo.springbootapis.security.oauth2;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.demo.springbootapis.model.security.User;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.repository.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@NonNull
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> 
			new UsernameNotFoundException("User not found with username : " + username));
		return UserPrincipal.create(user);
	}

	public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
}
