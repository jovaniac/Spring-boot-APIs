package com.demo.springbootapis.model.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class UserPrincipal implements OAuth2User, UserDetails {

	@NonNull
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private String name;
	private String email;

	@JsonIgnore
	private String password;

	private String imageUrl;

	@NonNull
	private Collection<? extends GrantedAuthority> authorities;

	private Map<String, Object> attributes;

	public UserPrincipal(@NonNull Long id, @NonNull String username, @NonNull String name, String password,
			String imageUrl, @NonNull Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.imageUrl = imageUrl;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());

		return new UserPrincipal(user.getId(), user.getUsername(), user.getLastName() + " " + user.getFirstName(),
				user.getPassword(), user.getImageUrl(), authorities);
	}

	public static UserPrincipal create(User user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
