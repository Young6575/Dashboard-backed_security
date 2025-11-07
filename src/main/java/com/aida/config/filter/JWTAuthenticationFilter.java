package com.aida.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aida.dto.LoginRequest;
import com.aida.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	
	//로그인 요청 시 실행
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
					loginRequest.userid(), loginRequest.password(),null);
			return authenticationManager.authenticate(authToken);
		} catch (Exception e) {
			throw new RuntimeException("로그인 정보 파싱 실패",e);
		}
	}
	
	// 인증 성공 시 실행
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User userDetails = (User) authResult.getPrincipal();
		String userid = userDetails.getUsername();
		
		String token = JWTUtil.getJWT(userid);
		response.addHeader("Authorization", token);
		
	}
	
	
	
	
	
}
