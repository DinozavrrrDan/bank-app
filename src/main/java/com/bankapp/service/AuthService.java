package com.bankapp.service;

import com.bankapp.enteties.jwt.JwtAuthentication;
import com.bankapp.enteties.request.JwtRequest;
import com.bankapp.enteties.response.JwtResponse;
import jakarta.security.auth.message.AuthException;
import org.springframework.lang.NonNull;

public interface AuthService {
    JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException;

    JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException;

    JwtResponse refresh(@NonNull String refreshToken) throws AuthException;

    JwtAuthentication getAuthInfo();
}
