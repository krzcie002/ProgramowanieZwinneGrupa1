package com.project.interfaces;

import com.project.auth.RegisterRequest;
import com.project.auth.Tokens;
import com.project.model.User;
import com.project.auth.Credentials;
import com.project.service.AuthService;

public interface IAuthService {
    void register(RegisterRequest user);
    Tokens authenticate(Credentials credentials);
    Tokens refreshTokens(Tokens tokens);
}
