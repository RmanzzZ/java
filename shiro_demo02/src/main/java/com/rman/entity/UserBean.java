package com.rman.entity;

import java.io.Serializable;

public class UserBean implements Serializable {
    private Integer  id;
    private String username;
    private String password;
    private String permission;
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Integer  getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public UserBean(Integer  id, String username, String password, String permission) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
}
