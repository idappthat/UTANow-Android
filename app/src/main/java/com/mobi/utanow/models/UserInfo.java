package com.mobi.utanow.models;

/**
 * Created by anthony on 3/2/16.
 */
public class UserInfo {
    String birthday;
    String email;
    String fbId;
    String fbToken;
    long fbTokenExpires;
    String gender;
    String name;
    String profileImage;
    String provider;

    public UserInfo() {}

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public long getFbTokenExpires() {
        return fbTokenExpires;
    }

    public void setFbTokenExpires(long fbTokenExpires) {
        this.fbTokenExpires = fbTokenExpires;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImage;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImage = profileImageUrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
