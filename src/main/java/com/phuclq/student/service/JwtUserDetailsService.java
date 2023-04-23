package com.phuclq.student.service;

import com.phuclq.student.domain.UserRole;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.phuclq.student.domain.User user = userRepository.findUserByEmailAndIsDeleted(email,false);
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(user.getRoleId());
        UserRole userRole = new UserRole();
        if (userRoleOptional.isPresent()) {
            userRole = userRoleOptional.get();
        }
        if (user != null) {
            return new User(user.getEmail(), user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userRole.getRole())));
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}

