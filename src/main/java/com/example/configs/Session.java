package com.example.configs;

import lombok.Getter;
import lombok.Setter;
import com.example.models.auth.AuthUser;

import java.util.Objects;

public class Session {
    @Getter
    @Setter
    private AuthUser user;
    private static Session session;

    private Session() {
        user = new AuthUser();
    }

    public static Session getInstance() {
        if (Objects.isNull(session)) {
            session = new Session();
        }
        return session;
    }
}
