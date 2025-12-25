package com.ramiaran.heritagesitesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.LinkedList;
import java.util.Stack;

public class HeritageController {


    private LinkedList<Visit> allVisits = new LinkedList<>();
    private Stack<Visit> recentBookings = new Stack<>();
    private LinkedList<HeritageSite> allSites = new LinkedList<>();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    // Initialize the 12 required sites
    public void initializeSites() {
        // Historical
        allSites.add(new HeritageSite("Erbil Citadel", "Erbil", "6000 BC", "Historical", "Ancient citadel"));
        allSites.add(new HeritageSite("Qaysari Bazaar", "Erbil", "13th Century", "Historical", "Market"));
        allSites.add(new HeritageSite("Minare Park", "Erbil", "12th Century", "Historical", "Tower"));
        allSites.add(new HeritageSite("Khanzad Castle", "Erbil", "16th Century", "Historical", "Fort"));

        // Museums
        allSites.add(new HeritageSite("Erbil Museum", "Erbil", "1989", "Museum", "Civilization"));
        allSites.add(new HeritageSite("Textile Museum", "Erbil", "2004", "Museum", "Carpets"));
        allSites.add(new HeritageSite("Slemani Museum", "Sulaymaniyah", "1961", "Museum", "Artifacts"));
        allSites.add(new HeritageSite("Gem Museum", "Erbil", "2010", "Museum", "Stones"));

        // Archaeological
        allSites.add(new HeritageSite("Shanidar Cave", "Bradost", "65000 BC", "Archaeological", "Neanderthals"));
        allSites.add(new HeritageSite("Jarmo", "Zagros", "7000 BC", "Archaeological", "Farming"));
        allSites.add(new HeritageSite("Barda Balka", "Chemchemal", "Paleolithic", "Archaeological", "Tools"));
        allSites.add(new HeritageSite("Halamata Cave", "Duhok", "Assyrian", "Archaeological", "Reliefs"));
    }
    // Option 2: Schedule a Visit
    public void scheduleVisit(String category, String siteName, String name, String id, String phone, java.time.LocalDate date) {

        // 1. Check traffic: How many times has this ID visited?
        // (We use 'long' because the count() method returns a long number)
        long count = allVisits.stream().filter(v -> v.getVisitorId().equals(id)).count();

        // 2. If limit reached (2 or more), generate fake verification code
        if (count >= 2) {
            int code = 100000 + new java.util.Random().nextInt(900000);
            System.out.println("Traffic Control: Limit reached for ID " + id);
            System.out.println("Verification Code sent to " + phone + ": " + code);
        }

        // 3. Create the Visit object
        Visit newVisit = new Visit(category, siteName, name, id, phone, date);

        // 4. Add to Memory
        allVisits.add(newVisit);        // Main Database
        recentBookings.push(newVisit);  // Stack for "Recent Bookings"

        System.out.println("Scheduled: " + name + " to " + siteName);
    }
}