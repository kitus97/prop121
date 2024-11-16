package com.prop.prop12_1.model;

public class User {

    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    @Override
    public String toString() {
        return username + ":" + password + ":" + role.getRoleName();
    }

    public static User fromString(String string) {
        String[] split = string.split(":");
        Role role = Role.fromString(split[2]);
        return new User(split[0], split[1], role);
    }
}
