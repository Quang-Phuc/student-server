package com.phuclq.student.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.phuclq.student.config.JwtTokenUtil;
import com.phuclq.student.dto.JwtRequest;
import com.phuclq.student.dto.JwtResponse;
import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.BusinessHandleException;
import com.phuclq.student.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String authenticate = authenticate(authenticationRequest.getEmail(),
            authenticationRequest.getPassword());
        if("400".equals(authenticate)){
            HttpStatus status =HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(authenticate);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ok(new JwtResponse(token));
    }

    private String authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BusinessHandleException("SS005");
        }
        return null;
    }
}
