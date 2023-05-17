package org.example.requests.user;

public class GetUserRequest implements org.example.utils.Request {
    private final String username;

    public GetUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
