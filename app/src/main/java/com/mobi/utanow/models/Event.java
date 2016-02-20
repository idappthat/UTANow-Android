package com.mobi.utanow.models;

/**
 * Created by Kevin on 2/12/2016.
 */
public class Event
{
    private String title;
    private String imgUrl;
    private String organization;
    private String description;

    public Event(String title, String description, String organization, String url)
    {
        this.title = title;
        this.description = description;
        this.organization = organization;
        this.imgUrl = url;
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
}
