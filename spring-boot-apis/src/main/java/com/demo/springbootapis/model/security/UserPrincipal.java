package com.demo.springbootapis.model.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class UserPrincipal implements UserDetails {
	
	private Long id;
	private String username;
	private String name;
	@JsonIgnore
	private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public static UserPrincipal createFrom(User user) {
    	List<GrantedAuthority> authorities = user.getRoles().stream().map(
    			role -> new SimpleGrantedAuthority(role.getAuthority())
		).collect(Collectors.toList());
    	
    	return new UserPrincipal(
	    			user.getId(),
	    			user.getUsername(),
	    			user.getLastName() + " " + user.getFirstName(),
	    			user.getPassword(),
	    			authorities
    			);
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
