package com.aida.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aida.Repository.UserRepository;
import com.aida.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUserid(userid)
				.orElseThrow(() -> new UsernameNotFoundException("해당 아이디를 찾을 수 없습니다." + userid));
		
		return new org.springframework.security.core.userdetails.User(
				userEntity.getUserid(), 
				userEntity.getPassword(),
				AuthorityUtils.createAuthorityList(userEntity.getRole().toString())
		);
	}

}
