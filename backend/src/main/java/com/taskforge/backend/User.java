package com.taskforge.backend;

import java.util.UUID;
import java.util.Objects;

public class User {
    // ID is final because it should not change once the user is created
    private final UUID id;
    private String username;
    private String email;
    private Role role;

    public User(String username, String email, Role role) {
        
        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User other))
            return false;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // getters
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    // setters

    public void setUsername(String username) {
        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        this.username = username;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        this.email = email;
    }

    public void setRole(Role role) {
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

}