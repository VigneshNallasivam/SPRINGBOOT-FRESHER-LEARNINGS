package com.java.basics.response;

import java.util.List;

public class UserInfoResponse {

    private Long id;
    private String refreshToken;
    private String username;
    private String password;
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public UserInfoResponse(Long id, String refreshToken, String username, String password, List<String> roles) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserInfoResponse() {

    }
}
