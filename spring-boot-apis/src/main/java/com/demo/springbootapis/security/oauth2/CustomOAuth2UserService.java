package com.demo.springbootapis.security.oauth2;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.springbootapis.controller.AppException;
import com.demo.springbootapis.exception.OAuth2AuthenticationProcessingException;
import com.demo.springbootapis.model.security.AuthProvider;
import com.demo.springbootapis.model.security.Role;
import com.demo.springbootapis.model.security.RoleName;
import com.demo.springbootapis.model.security.User;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.repository.RoleRepository;
import com.demo.springbootapis.repository.UserRepository;
import com.demo.springbootapis.security.oauth2.user.OAuth2UserInfo;
import com.demo.springbootapis.security.oauth2.user.OAuth2UserInfoFactory;
import com.demo.springbootapis.util.RandomStringGenerator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@NonNull
	private UserRepository userRepository;

	@NonNull
	RoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		try {
			return processOAuth2User(userRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}

	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
		User user = null;
		if (userOptional.isPresent()) {// the user has registered before
			user = userOptional.get();
			if (!user.getProvider()
					.equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			updateExistingUser(user, oAuth2UserInfo);
		} else {// User not found, create a new user
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		User user = new User();
		// Since Username is required for UserPrincipl, we generate a random
		// one.
		user.setUsername(RandomStringGenerator.getAlphaNumericString(25));
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oAuth2UserInfo.getId());
		user.setFirstName(oAuth2UserInfo.getFirstName());
		user.setLastName(oAuth2UserInfo.getLastName());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		user.setActive(true);

		// For demo purpose, set user to have admin role, so he/she can view all
		// pages.
		Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new AppException("User Role not set."));
		user.setRoles(Collections.singletonList(userRole));

		return userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setFirstName(oAuth2UserInfo.getFirstName());
		existingUser.setLastName(oAuth2UserInfo.getLastName());
		existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());

		return userRepository.save(existingUser);
	}
}
