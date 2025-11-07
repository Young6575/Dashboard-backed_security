package com.aida.service;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aida.Repository.UserRepository;
import com.aida.domain.Gender;
import com.aida.domain.User;
import com.aida.dto.LoginRequest;
import com.aida.dto.SignupRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	// 4~20자, 영문/숫자/._ 허용, 시작/끝 '.' 금지, '..' 금지
    private static final Pattern USERNAME_RULE =
        Pattern.compile("^(?=.{4,20}$)(?!.*\\.\\.)[a-zA-Z0-9](?:[a-zA-Z0-9._]*[a-zA-Z0-9])$");
	
    // ============= 중복 아이디 검사 =============
	public CheckIdResult check(String userid) {
		
		String uid = userid == null ? "" : userid.trim().toLowerCase();
        if (!USERNAME_RULE.matcher(uid).matches()) {
            return new CheckIdResult(false, "아이디 형식이 맞지않습니다."); // 형식 불일치
        }
		
		boolean exists  = userRepo.existsByUserid(userid);
		return new CheckIdResult(exists , exists  ? "중복 아이디입니다!" : "사용 가능한 아이디입니다!");
		
	}
    public record CheckIdResult(boolean available, String reason) {}
    
    
    
    // ============= 가입정보 User 객체주입 및 저장 =============
    public void register(SignupRequest request) {
    	// 아이디 2차 중복 검사
    	if(userRepo.existsByUserid(request.userid())) {
    		throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    	}
    	
    	// 비밀번호 암호화
    	String encodedPassword = passwordEncoder.encode(request.password());
    	
    	
    	
    	// User 객체에 담기
    	User newUser = User.builder()
                .userid(request.userid())
                .password(encodedPassword)
                .email(request.email())
                .birthDate(LocalDate.parse(request.birthDate()))
                .name(request.name())
                
                // String으로 받은 gender를 Gender Enum 타입으로 변환
                .gender(Gender.valueOf(request.gender().toUpperCase()))
                
                .phoneNumber(request.phoneNumber())
                .organization(request.organization())
                .region(request.region())
                .interests(request.interests().toString())
                // id, createdAt, role은 자동으로 생성/설정되므로 여기서 지정할 필요 없음
                .build();
    	
    	userRepo.save(newUser);
    	
    }
 
    // ============= 로그인 검사 =============
    public User login(LoginRequest loginRequest) {
    	
    	
    	// 1) 아이디로 사용자 조회
    	User user =  userRepo.findByUserid(loginRequest.userid())
    			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다"));
    	
    	// 2) 비밀번호 확인
    	if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
    	    throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
    	}
    	

        // 3) 여기까지 통과하면 로그인 성공
        return user;
    	
    	
    }
    
    
    
    

}
