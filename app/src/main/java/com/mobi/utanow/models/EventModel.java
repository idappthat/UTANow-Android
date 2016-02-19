package com.mobi.utanow.models;

/**
 * Created by anthony on 2/6/16.
 */
public class EventModel {
    //ToDo give events real data.

    String eventName;
    String clubName;
    String description;

    public EventModel(String eventName,String clubName, String description){
        this.eventName = eventName;
        this.clubName = clubName;
        this.description = description;
    }
    public String getEventName(){return eventName;}
    public String getClubName(){return clubName;}
    public String getDiscription(){return description;}
}
