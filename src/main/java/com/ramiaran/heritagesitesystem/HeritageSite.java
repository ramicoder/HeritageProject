package com.ramiaran.heritagesitesystem;

public class HeritageSite {
    private String name;
    private String location;
    private String era;
    private String category;
    private String description;

    public HeritageSite(String name, String location, String era, String category, String description) {
        this.name = name;
        this.location = location;
        this.era = era;
        this.category = category;
        this.description = description;
    }

    // Getters so we can read the info later
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getEra() { return era; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
}