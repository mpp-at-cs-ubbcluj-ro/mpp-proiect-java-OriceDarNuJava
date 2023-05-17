package org.example.responses.user;

import org.example.domain.User;

public class SuccessAuthenticationResponse implements org.example.utils.Response {
    private final User user;

    public SuccessAuthenticationResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
