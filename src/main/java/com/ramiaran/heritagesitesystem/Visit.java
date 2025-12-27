package com.ramiaran.heritagesitesystem;

import java.time.LocalDate;
// The data types are private for security and control purposes, this way they are read-only and not changeable by other areas of our data.
public class Visit {
    private String category;
    private String siteName;
    private String visitorName;
    private String visitorId;
    private String phoneNumber; // Added for verification/6-digit code
    private LocalDate visitDate;

    public Visit(String category, String siteName, String visitorName, String visitorId, String phoneNumber, LocalDate visitDate) {
        this.category = category;
        this.siteName = siteName;
        this.visitorName = visitorName;
        this.visitorId = visitorId;
        this.phoneNumber = phoneNumber;
        this.visitDate = visitDate;
    }

    // Getters
    public String getCategory() { return category; }
    public String getSiteName() { return siteName; }
    public String getVisitorName() { return visitorName; }
    public String getVisitorId() { return visitorId; }
    public String getPhoneNumber() { return phoneNumber; } // Added getter
    public LocalDate getVisitDate() { return visitDate; }
}
// 25/12/2025, added the visit class
