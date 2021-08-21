package com.app.springrolejwt.security;

import com.app.springrolejwt.repository.implementation.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j
public class JwtUtils {

	@Value("${jwtSecret}")
	private String jwtSecret;

	@Value("${jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}" + e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}" + e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}" + e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}"+ e.getMessage());
		}

		return false;
	}
}