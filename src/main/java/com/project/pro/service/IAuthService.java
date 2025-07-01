package com.project.pro.service;

import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

     ResponseEntity<JwtAuthenticationResponse> getJwtAuthenticationResponseEntity(LoginRequest loginRequest);

}
