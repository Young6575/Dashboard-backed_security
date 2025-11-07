package com.aida.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aida.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUserid(String userid);

    // 1. userid로 사용자를 조회하기 위한 메서드 (로그인, 토큰 검증 시 사용)
    // "userid 컬럼에서 파라미터로 받은 userid와 일치하는 User를 찾아서 Optional<User> 타입으로 반환해줘"
    // 라는 의미의 SQL 쿼리를 자동으로 생성해줍니다.
    Optional<User> findByUserid(String userid);
}
