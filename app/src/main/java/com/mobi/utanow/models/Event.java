package com.mobi.utanow.models;

/**
 * Created by Kevin on 2/12/2016.
 */
public class Event {
    String title;
    String image;
    String organizationImage;
    String organization;
    String description;


    public Event() {
    }

    public Event(String title, String description, String organization, String image)
    {
        this.title = title;
        this.description = description;
        this.organization = organization;
        this.image = image;
    }
    public Event(){
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getOrganization() {
        return organization;
    }

    public String getDescription() {
        return description;
    }

    public String getOrginaztionImage() {
        return organizationImage;
    }
}
