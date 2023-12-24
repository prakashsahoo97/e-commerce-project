package com.prakash.configuration;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken=request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwtToken!=null) {
			
			jwtToken=jwtToken.substring(7);
			
			try {
				SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
				String email=String.valueOf(claims.get("email"));
				String auth=String.valueOf(claims.get("authorities"));
				List<GrantedAuthority> authorities=AuthorityUtils.commaSeparatedStringToAuthorityList(auth);
				Authentication authentication=new UsernamePasswordAuthenticationToken(email, null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				throw new BadCredentialsException("invalid token...");
			}
		}
		
		filterChain.doFilter(request, response);
		
		
	}

}
