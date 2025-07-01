package com.project.pro.service.impl;

import com.project.pro.config.security.JwtTokenProvider;
import com.project.pro.enums.EnumRole;
import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> getJwtAuthenticationResponseEntity(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication, EnumRole.ADMIN, "");
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

}
