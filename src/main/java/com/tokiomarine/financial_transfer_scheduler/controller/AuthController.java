package com.tokiomarine.financial_transfer_scheduler.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokiomarine.financial_transfer_scheduler.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final JwtUtil jwtUtil;

	public AuthController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
		//TODO aqui mantive assim só para fins de exemplo, o ideal é usar o banco
		if ("admin".equals(body.get("username")) && "admin".equals(body.get("password"))) {
			return ResponseEntity.ok(jwtUtil.generateToken(body.get("username")));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}
}
