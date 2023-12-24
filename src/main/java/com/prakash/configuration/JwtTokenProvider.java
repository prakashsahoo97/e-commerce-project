package com.prakash.configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth) {

		String jwtToken = Jwts.builder()
						 .setIssuedAt(new Date())
						 .setExpiration(new Date(new Date().getTime() + 86400000))
						 .claim("email", auth.getName())
						 .signWith(key)
						 .compact();

		return jwtToken;
	}

	public String getEmailFromJwtToken(String jwtToken) {
		jwtToken = jwtToken.substring(7);

		Claims claims = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(jwtToken)
							.getBody();
		String email = String.valueOf(claims.get("email"));

		return email;
	}

	/*
	 * public String populateAuthorities(Collection<? extends GrantedAuthority>
	 * collection) { Set<String> auths = new HashSet<>();
	 * 
	 * for (GrantedAuthority authority : collection) {
	 * auths.add(authority.getAuthority()); } return String.join(",", auths); }
	 */

}
