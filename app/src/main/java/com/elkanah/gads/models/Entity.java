package com.elkanah.gads.models;

public class Entity {
    public String EmailAddress;
    public String Name;
    public  String LastName;
    public String Link;

    public Entity(String emailAddress, String name, String lastName, String link) {
        EmailAddress = emailAddress;
        Name = name;
        LastName = lastName;
        Link = link;
    }
}
