package com.aida.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aida.domain.User;
import com.aida.dto.LoginRequest;
import com.aida.dto.SignupRequest;
import com.aida.service.SignupService;
import com.aida.service.SignupService.CheckIdResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SingupController {
	
	private final SignupService signupService;
	
	
    @GetMapping("/checkid")
    public ResponseEntity<?> checkid(@RequestParam String userid) {
        CheckIdResult r = signupService.check(userid);
        return ResponseEntity.ok(Map.of("중복여부", r.available(), "msg", r.reason()));
    }
	
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody SignupRequest signRequest) {
        try {
            signupService.register(signRequest);

            // ✅ 성공 응답
            return ResponseEntity.ok(Map.of(
                "result", true,
                "msg", "성공"
            ));

        } catch (IllegalArgumentException e) {
            // ✅ 잘못된 요청 (예: 중복 아이디)
            return ResponseEntity.badRequest().body(Map.of(
                "result", false,
                "msg", e.getMessage()
            ));

        } catch (Exception e) {
            // ✅ 그 외 서버 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "result", false,
                "msg", "서버 오류가 발생했습니다."
            ));
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
        	User user = signupService.login(loginRequest);
        	
        	return ResponseEntity.ok(Map.of(
                    "result", true,
                    "data", user  
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "result", false,
                "msg", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "result", false,
                "msg", "서버 오류가 발생했습니다."
            ));
        }
    }
    
    
	
	

	
	
}
