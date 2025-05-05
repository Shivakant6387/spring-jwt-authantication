package com.example.spring_boot_role_base_authentication.dto;


public class JwtResponse {
    private long userId;
    private String username;
    private RoleDto roles;
    private String token;

    public JwtResponse(long userId, String username, RoleDto roles, String token) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleDto getRoles() {
        return roles;
    }

    public void setRoles(RoleDto roles) {
        this.roles = roles;
    }
}
