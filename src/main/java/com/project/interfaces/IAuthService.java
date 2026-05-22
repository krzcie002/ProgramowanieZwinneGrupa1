package com.project.interfaces;

import com.project.auth.RegisterRequest;
import com.project.auth.Tokens;
import com.project.auth.RefreshTokenRequest;
import com.project.auth.Credentials;

public interface IAuthService {
    void register(RegisterRequest user);
    Tokens authenticate(Credentials credentials);
    Tokens refreshTokens(RefreshTokenRequest request);
}
