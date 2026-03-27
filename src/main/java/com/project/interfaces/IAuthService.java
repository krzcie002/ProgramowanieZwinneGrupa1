package com.project.interfaces;

import com.project.model.User;

public interface IAuthService {
    void register(User user);
    Tokens authenticate(Credentials credentials);
    Tokens refreshTokens(Tokens tokens);
}
