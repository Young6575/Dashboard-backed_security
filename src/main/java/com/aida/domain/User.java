package com.aida.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users") // 테이블명 명시
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 생성
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    
    private String userid; // 로그인 아이디(사용자 입력). 저장 전 소문자/trim 권장
    
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // 가입일(자동 기록)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Role role = Role.MEMBER;

    // 성별 (우리 enum 사용)
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender; // MALE/FEMALE 저장, 화면 표시: gender.display()

    // 생년월일
    private LocalDate birthDate;

    // 연락처
    @Column(length = 20)
    private String phoneNumber;

    // 소속
    private String organization;

    // 지역
    private String region;
    

    // 관심법안 (MVP: 문자열 그대로 저장)
    @Column(length = 1000)
    private String interests; // 예: "개인정보보호법","중대재해처벌법"

}
