package com.ssafy.smaheal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.smaheal.exception.ResourceNotFoundException;
import com.ssafy.smaheal.model.MemberUser;
import com.ssafy.smaheal.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
//        MemberUser user = userRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with email : " + email)
//        );
//
//        return UserPrincipal.create(user);
    	return null;
    }

    @Transactional
    public UserDetails loadUserByNum(Long num) {
        MemberUser user = userRepository.findByNum(num).orElseThrow(
            () -> new ResourceNotFoundException("User", "num", num)
        );

        return UserPrincipal.create(user);
    }
}