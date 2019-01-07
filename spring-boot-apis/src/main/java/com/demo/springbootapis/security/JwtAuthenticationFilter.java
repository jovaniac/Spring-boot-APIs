package com.demo.springbootapis.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.demo.springbootapis.model.security.User;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.repository.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(String url, AuthenticationManager authenticationManager) {
    	super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }
    
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
		try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	 protected void successfulAuthentication(HttpServletRequest request, 
			 HttpServletResponse response, FilterChain chain, 
			 Authentication authentication)
	 throws IOException, ServletException {
		if (jwtTokenProvider==null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			jwtTokenProvider = webApplicationContext.getBean(JwtTokenProvider.class);
		}
		String token = jwtTokenProvider.generateToken(authentication); 
		
		JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(token, (UserPrincipal)authentication.getPrincipal());
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(jwtToken);
        response.getWriter().print(jsonInString);
        response.getWriter().flush();
	}
}
