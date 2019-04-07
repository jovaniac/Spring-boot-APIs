package com.demo.springbootapis.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.springbootapis.config.AppProperties;
import com.demo.springbootapis.exception.OAuth2AuthenticationProcessingException;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.security.JwtAuthenticationToken;
import com.demo.springbootapis.security.JwtTokenProvider;
import com.demo.springbootapis.util.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@NonNull
	private AppProperties appProperties;

	@NonNull
	private JwtTokenProvider tokenProvider;

	@NonNull
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		Optional<String> redirectUri = CookieUtils
				.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);

		if (!redirectUri.isPresent() || !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		String token = tokenProvider.generateToken(authentication);

		JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(token,
				(UserPrincipal) authentication.getPrincipal());
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(jwtToken);

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("OAuth2Response", jsonInString).build()
				.toUriString();
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		URI authorizedURI = URI.create(appProperties.getOauth2().getAuthorizedRedirectUris());
		if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
				&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
			return true;
		}
		return false;
	}
}
