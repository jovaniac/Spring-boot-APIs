package com.demo.springbootapis.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.demo.springbootapis.config.AppProperties;
import com.demo.springbootapis.model.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Autowired
	private AppProperties appProperties;

    
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";
    private static final String CLAIM_KEY_USERNAME = "username";
    
    public String generateToken(Authentication authentication) {
    	UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
    	
    	Date now = new Date();
    	Date expiration = new Date(now.getTime() + appProperties.getJwt().getTokenExpirationMsec());
    	
    	Claims claims = Jwts.claims().setSubject(String.valueOf(userPrincipal.getId()));
    	claims.put(CLAIM_KEY_USERNAME, userPrincipal.getUsername());
    	claims.put(CLAIM_KEY_AUTHORITIES, userPrincipal.getAuthorities());
	    
    	return Jwts.builder()
    			.setClaims(claims)
    			.setIssuedAt(now)
    			.setExpiration(expiration)
    			.signWith(SignatureAlgorithm.HS512, appProperties.getJwt().getTokenSecret())
    			.compact();	
    }
    
    public Long getUserIdFromJWT(String token) {
    	Claims claims = Jwts.parser().setSigningKey(appProperties.getJwt().getTokenSecret())
    								.parseClaimsJws(token).getBody();
    	return Long.parseLong(claims.getSubject());
    }
    
    //Not finished yet
    public UserPrincipal getUserPrincipalFromJWT(String token) {
    	Claims claims = Jwts.parser().setSigningKey(appProperties.getJwt().getTokenSecret())
				.parseClaimsJws(token).getBody();
    	
    	
    	//TODO: get claim and authorities and create a new UserPrincipal and return.
    	claims.get(CLAIM_KEY_AUTHORITIES);
    	claims.get(CLAIM_KEY_USERNAME);
    	
    	return null;
    }
    
    public boolean validateToken(String token) {
    	try {
            Jwts.parser().setSigningKey(appProperties.getJwt().getTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
