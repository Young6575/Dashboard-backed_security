package com.aida.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
    String userid,

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password,
    String name,
    String email,
    String gender,          // "MALE"/"FEMALE" 또는 "남성"/"여성" 허용(서비스에서 매핑)
    String birthDate,       // "YYYY-MM-DD"
    String phoneNumber,
    String organization,
    String region,
    String	 interests       // 그대로 문자열 저장(MVP 기준)

    // ※ 현재 User 엔티티가 email not null이면 여기 email 필드도 추가하거나, 엔티티 제약 완화 필요
) {}