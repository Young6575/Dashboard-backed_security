package com.aida.util; // 패키지 경로는 본인 프로젝트에 맞게 수정하세요.

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public final class JWTUtil {

    // 비밀키: 실제 운영 환경에서는 노출되지 않도록 application.properties 등에서 관리해야 합니다.
    private static final String JWT_KEY = "edu.pnu.jwtkey"; // 
    
    // 토큰 Prefix
    public static final String prefix = "Bearer "; // 
    
    // username을 담을 Claim의 Key 값
    public static final String usernameClaim = "username"; // 

    // 토큰 유효시간: 24시간 (1일)
    private static final long ACCESS_TOKEN_MSEC = 24 * 60 * 60 * 1000; // 

    // private 생성자로 외부에서 인스턴스 생성을 막습니다.
    private JWTUtil() {}

    /**
     * 사용자 이름을 기반으로 JWT를 생성합니다.
     * @param username 토큰에 담을 사용자 이름
     * @return "Bearer "가 포함된 전체 JWT 문자열
     */
    public static String getJWT(String username) { // 
        String src = JWT.create()
                .withClaim(usernameClaim, username) // 
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MSEC)) // 
                .sign(Algorithm.HMAC256(JWT_KEY)); // 
        return prefix + src; // 
    }
}