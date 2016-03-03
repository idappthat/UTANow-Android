package com.mobi.utanow.models;

/**
 * Created by anthony on 3/2/16.
 */
public class User {
    String email;
    String name;
    String profileImageUrl;

    public User() {}

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
