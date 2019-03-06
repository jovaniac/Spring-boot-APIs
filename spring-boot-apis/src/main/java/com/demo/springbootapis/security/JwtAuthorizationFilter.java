package com.demo.springbootapis.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.demo.springbootapis.repository.SecurityConstants;
import com.demo.springbootapis.security.oauth2.CustomUserDetailsService;

public class JwtAuthorizationFilter extends GenericFilterBean {
	
    private JwtTokenProvider jwtTokenProvider;
	
	private CustomUserDetailsService userDetailService;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			//Initialize jwtTokenProvider and userDetailService
			if (jwtTokenProvider == null || userDetailService == null) {
				ServletContext servletContext = request.getServletContext();
				WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				if (jwtTokenProvider == null) {
					jwtTokenProvider = webApplicationContext.getBean(JwtTokenProvider.class);
				} 
				if (userDetailService == null) {
					userDetailService = webApplicationContext.getBean(CustomUserDetailsService.class);
				}
			}
			String token = getJwtFromRequest((HttpServletRequest)request);
			if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				Long userID = jwtTokenProvider.getUserIdFromJWT(token);
				
				UserDetails userDetails = userDetailService.loadUserById(userID);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}
		
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
