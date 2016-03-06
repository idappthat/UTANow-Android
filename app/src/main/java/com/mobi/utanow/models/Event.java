package com.mobi.utanow.models;

/**
 * Created by Kevin on 2/12/2016.
 */
public class Event {
    String title;
    String imgUrl;
    String organizationImg;
    String organization;
    String description;
    String coords;


    public Event() {
    }

    public Event(String title, String description, String organization, String image)
    {
        this.title = title;
        this.description = description;
        this.organization = organization;
        this.imgUrl = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getOrganization() {
        return organization;
    }

    public String getDescription() {
        return description;
    }

    public String getCoords() {
        return coords;
    }

    public String getOrganizationImg() {
        return organizationImg;
    }
}
