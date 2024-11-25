package com.bankapp.enteties.response;

public class JwtResponse {

    private final String type = "Bearer";
    private final String accessToken;
    private final String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
