package com.tokiomarine.financial_transfer_scheduler.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expirationTimeInMillis;

	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuer("TransferApp")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public String validateToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			return claims.getSubject();
		} catch (JwtException e) {
			return null;
		}
	}
}
