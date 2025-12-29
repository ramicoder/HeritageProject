package com.ramiaran.heritagesitesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Iterator;

public class HeritageController {

    // The variables for our UI components  -Rami
    // They link our Java code to the specific boxes on the screen.
    // If the FXML file has fx:id="nameField", this variable grabs it.
    @FXML private javafx.scene.control.ComboBox<String> categoryBox; // The dropdown for categories
    @FXML private javafx.scene.control.ComboBox<String> siteBox;     // The dropdown for specific sites
    @FXML private javafx.scene.control.TextField nameField;          // Input box for visitor Name
    @FXML private javafx.scene.control.TextField idField;            // Input box for visitor ID
    @FXML private javafx.scene.control.TextField phoneField;         // Input box for phone number
    @FXML private javafx.scene.control.DatePicker datePicker;        // The calendar popup
    @FXML private javafx.scene.control.TextArea feedbackArea;        // The big text box at the bottom for messages/errors
    @FXML private javafx.scene.control.TextField searchField;        // Input for search
    @FXML private javafx.scene.control.CheckBox ascendingCheck;      // Checkbox for sorting
    @FXML private javafx.scene.control.ComboBox<String> viewCategoryBox; // Filter dropdown for Option 4

    // This is for the tableview holding value of the visit object we made
    @FXML private javafx.scene.control.TableView<Visit> resultsTable;

    // These columns tell the table which part of the Visit object to show.
    @FXML private javafx.scene.control.TableColumn<Visit, String> colName;     // Shows visit.getVisitorName()
    @FXML private javafx.scene.control.TableColumn<Visit, String> colSite;     // Shows visit.getSiteName()
    @FXML private javafx.scene.control.TableColumn<Visit, String> colCategory; // Shows visit.getCategory()
    @FXML private javafx.scene.control.TableColumn<Visit, String> colDate;     // Shows visit.getVisitDate()

    // This method is created so that JavaFX runs it automatically when the screen opens.
    // We use it to set up the starting state of the app.
    @FXML
    public void initialize() {
        initializeSites(); // Calls our existing method to load the 12 heritage sites into memory.

        // Add the 3 options to the Category Dropdown so the user can pick them.
        categoryBox.getItems().addAll("Historical", "Museum", "Archaeological");
        viewCategoryBox.getItems().addAll("Historical", "Museum", "Archaeological");

        // This code is ran when a user picks a category
        categoryBox.setOnAction(e -> {
            String selected = categoryBox.getValue(); // Get what they picked
            // If they picked something, find the matching sites and put them in the Site Dropdown.
            if (selected != null) {
                siteBox.getItems().setAll(getSiteNamesByCategory(selected));
            }
        });

        // This connects the Table Columns to our Visit.java class.
        // "visitorName" must match the variable name in our Visit class exactly!
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("visitorName"));
        colSite.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("siteName"));
        colCategory.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("visitDate"));
    }

    private LinkedList<Visit> allVisits = new LinkedList<>();
    private Stack<Visit> recentBookings = new Stack<>();
    private LinkedList<HeritageSite> allSites = new LinkedList<>();

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
    // Option 1 Rami- Aran
    public String[] getCategories() {
        return new String[]{"Historical Sites", "Museums", "Archaeological Sites"}; // This is displaying the heritage site categories and the input is taken in the UI
    }


    // Option 2: Schedule a Visit - rami aran
    public String scheduleVisit(String category, String siteName, String name, String id, String phone, java.time.LocalDate date) {
        // validation check, making sure no empty input is done
        if (category == null || siteName == null || name.isBlank() || id.isBlank() || phone.isBlank() || date == null) {
            return "Error: All fields are required.";
        }
        // validate that date is not in the past
        if (date.isBefore(java.time.LocalDate.now())) {
            return "Error: You cannot book a visit in the past.";
        }
        StringBuilder bob = new StringBuilder();

        // 1. counting how many times the visitor visited based on the id
        int count = 0;
        for (Visit v : allVisits) {
            if (v.getVisitorId().equalsIgnoreCase(id)) {
                count++;
            }
        }

        // 2. if visitor count reached 2 or more, generate verification code
        if (count >= 2) {
            int code = 100000 + new java.util.Random().nextInt(900000);
            bob.append("Traffic Warning: Frequent Visitor Limit (").append(count).append(")\n");
            bob.append("Verification Code sent to ").append(phone).append(": ").append(code).append("\n");
        }

        // 3. Create the Visit object
        Visit newVisit = new Visit(category, siteName, name, id, phone, date);

        // 4. Adding the new visit to the bookings stack and all visits linked list
        allVisits.add(newVisit);
        recentBookings.push(newVisit);

        // 5. Final Success Message
        bob.append("Scheduled: ").append(name).append(" to ").append(siteName);

        // Return String for UI
        return bob.toString();
    }
    // Option 2 Helper: Used by the UI to filter sites in the Dropdown
    public java.util.List<String> getSiteNamesByCategory(String category) {
        java.util.List<String> names = new java.util.ArrayList<>();

        // Loop through all sites
        for (HeritageSite site : allSites) {
            // if the site matches the category (e.g., "Museum"), add its name to the list
            if (site.getCategory().equalsIgnoreCase(category)) {
                names.add(site.getName());
            }
        }
        return names; // give the needed list back to the UI
    }
    //option 3 is to cancel a visit based on a category, site name, and visitor id- Aran
public boolean cancelVisit(String category, String siteName, String visitorId){
if (category == null || siteName == null || visitorId == null)
    return false;
if (category.isBlank() || siteName.isBlank() || visitorId.isBlank())
    return false; //line 67-68 are just validators in order to make sure there is something to delete
Iterator<Visit> pointer = allVisits.iterator();
while (pointer.hasNext()){
  Visit v = pointer.next(); // v is the current visit it is pointing at
    // hasNext() checks if theres an element after the current one
    // Next moves the pointer to the next element
    // the following loop is to compare using the getters in "Visit" to make sure we are deleting the correct visit, and it gives the metehod a return value
    if (
            v.getCategory().equalsIgnoreCase(category) &&
                    v.getSiteName().equalsIgnoreCase(siteName) &&
                    v.getVisitorId().equalsIgnoreCase(visitorId)
    ){
        pointer.remove(); //Safe Deletion, not masking.
        return true;
    }

}
return false;

    }

    // Option 4: View Visits (Return list for UI) -Rami
    public LinkedList<Visit> viewVisits(String category, boolean ascending) {

        // 1. Filter: specific category only
        // We use a temporary list so we don't mess up the main 'allVisits' list
        LinkedList<Visit> filteredList = new LinkedList<>();
        for (Visit v : allVisits) {
            // using a linear search to traverse through our linked list and add it to our new list if matching the category
            if (v.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(v);
            }
        }

        // 2. Sort: Explicit Comparator
        //soring with timsort(a combination of merge and insertion sort with a big O of Nlog(N))
        //the lamba expression is its syntax which sorts based on the compareto values when comparing the dates
        filteredList.sort((v1, v2) -> {
            if (ascending) return v1.getVisitDate().compareTo(v2.getVisitDate());
            else return v2.getVisitDate().compareTo(v1.getVisitDate());
        });
        // Return the list to the UI
        return filteredList;
    }


// Option 5: Display Totals (Return String for UI)- Rami
    public String displayTotals() {
        StringBuilder bob = new StringBuilder();
        bob.append("--- System Totals ---\n");

        // 1. Get unique categories
        LinkedList<String> categories = new LinkedList<>(); // We did an Array initially, but AI recommended that we use a linked list for cleanliness and efficiency.
        for (HeritageSite site : allSites) { //we traverse through all sites and get the unique categories instead of having redundancy
            boolean exists = false;
            for(String c : categories) {
                if(c.equalsIgnoreCase(site.getCategory())){
                    exists = true;
                    break; //once we get the category we break because we don't need to go through the other categories after we found one matching
                }
            }
            if (!exists) categories.add(site.getCategory());
        }

        // 2. Loop through Categories
        for (String cat : categories) {
            // Count total visits for this category
            int catCount = 0;
            for (Visit v : allVisits) {
                if (v.getCategory().equalsIgnoreCase(cat)) {
                    catCount++;
                }
            }
            bob.append(cat).append(": ").append(catCount).append(" total visits\n");

            // 3. same logic as for category but for sites now
            for (HeritageSite site : allSites) {
                if (site.getCategory().equalsIgnoreCase(cat)) {
                    int siteCount = 0;
                    for (Visit v : allVisits) {
                        if (v.getSiteName().equalsIgnoreCase(site.getName())) {
                            siteCount++;
                        }
                    }
                    bob.append("   - ").append(site.getName().trim()).append(": ").append(siteCount).append("\n");
                }
            }
        }
        return bob.toString();
    }

/*Option 6, searching for a visitor - Aran
  The method creates a new linked lists which acts like a basket in order to display the
  results of the search to the user.
*/
    public LinkedList<Visit> searchVisitor(String visitorId, String visitorName){

    /* the following is to create the new temporary linked list
       it is also worthy to note that a visitor can have several visits, that is why an ordinary string is not
       being made but instead a linked list
    */
        LinkedList<Visit> SearchResult = new LinkedList<>();

        boolean hasId = visitorId != null && !visitorId.isBlank();
        //This is to check if the user provided valid ID to search for

        boolean hasName = visitorName != null && !visitorName.isBlank();
        // this is to checl if the user provided a valid Name that is stored

        if (!hasId && !hasName){
            return SearchResult;
        }

    /* The following portion is to prioritize ID search over Name seaarch, although it will still search for the
       name, the Id is prioritized over it
    */
        if (hasId) {

            visitorId = visitorId.trim().toLowerCase();

            for (Visit v : allVisits) { // traverses the visits
                if (v.getVisitorId().toLowerCase().equals(visitorId)){
                    SearchResult.add(v);

                /* this is a conditonal loop, not a nested loop, before I wrote one big loop, but it was messy
                   and hard to understand so AI helped me make it more clean and easier to understand
                */
                }
            }
            return SearchResult;
        }

        for (Visit v : allVisits){
            if (v.getVisitorName().toLowerCase().equals(visitorName.trim().toLowerCase())){
                SearchResult.add(v);
            }
        }

        return SearchResult;
    }


    /* Option 7: Get the most recently scheduled visits - Aran
       This method returns the latest bookings (up to 10) in newest-to-oldest order.
       It returns a LinkedList so the UI can display the results (e.g., in a TableView)
       instead of printing directly to the console.
    */
    public LinkedList<Visit> getRecentVisits() {

        // This LinkedList acts as a basket to store the recent visits for the UI
        LinkedList<Visit> result = new LinkedList<>();

        // Validate that there are recent bookings stored in the stack
        if (recentBookings.isEmpty()) {
            return result; // return empty list if there are no bookings
        }

        // Limit ensures we do not exceed 10 visits or the stack size
        int limit = Math.min(10, recentBookings.size());

    /*
       The stack stores visits in LIFO order (Last In, First Out).
       - The most recent visit is at index size - 1
       - We loop backwards to display the newest visits first
       - The loop stops once we reach the defined limit
    */
        for (int i = recentBookings.size() - 1; i >= recentBookings.size() - limit; i--) {

            Visit v = recentBookings.get(i);

        /*
           A new Visit object is created with trimmed values to ensure
           clean and consistent data is displayed in the UI.
           The original Visit objects stored in memory are not modified.
        */
            Visit cleanVisit = new Visit(
                    v.getCategory().trim(),
                    v.getSiteName().trim(),
                    v.getVisitorName().trim(),
                    v.getVisitorId().trim(),
                    v.getPhoneNumber().trim(),
                    v.getVisitDate()
            );

            result.add(cleanVisit);
        }

        // Return the list of recent visits to be displayed by the UI
        return result;
    }
    // Option 8 - Rami
// Option 8: Summary Report (Return String for UI)
    public String generateSummaryReport() {

        StringBuilder bob = new StringBuilder();
        bob.append("--- Intelligence Report ---\n");

        // --- Find Top Category ---
        String topCategory = "None";
        int maxCatCount = -1;

        // Get unique categories manually using a new linked list
        // also no worries if matches same name as another linked list because this is declared locally so the scoping took care of that
        LinkedList<String> categories = new LinkedList<>();
        //we use a nested loop to only get the unique categories within all our sites like what we did in option 5
        for (HeritageSite s : allSites) {
            boolean present = false;
            for (String c : categories) {
                if (c.equalsIgnoreCase(s.getCategory())) {
                    present = true;
                    break;
                }
            }
            if (!present) {
                categories.add(s.getCategory());
            }
        }

        // Count visits per category
        for (String cat : categories) {
            int count = 0;
            for (Visit v : allVisits) {
                if (v.getCategory().equalsIgnoreCase(cat)) {
                    count++;
                }
            }
            // we find the name for the category with most visits and its visit count
            if (count > maxCatCount) {
                maxCatCount = count;
                topCategory = cat;
            }
        }

        bob.append("Top Category: ")
                .append(topCategory)
                .append(" (")
                .append(maxCatCount)
                .append(" visits)\n");

        // same logic as above but with site
        String topSite = "None";
        int maxSiteCount = -1;

        for (HeritageSite site : allSites) {
            int count = 0;
            for (Visit v : allVisits) {
                if (v.getSiteName().equalsIgnoreCase(site.getName())) {
                    count++;
                }
            }

            if (count > maxSiteCount) {
                maxSiteCount = count;
                topSite = site.getName();
            }
        }

        bob.append("Top Site:     ")
                .append(topSite)
                .append(" (")
                .append(maxSiteCount)
                .append(" visits)\n");

        return bob.toString();
    }

    // These methods run when you click the buttons in the GUI.

    @FXML
    void onScheduleClick() {
        // 1. Call the existing logic method
        String result = scheduleVisit(
                categoryBox.getValue(),
                siteBox.getValue(),
                nameField.getText(),
                idField.getText(),
                phoneField.getText(),
                datePicker.getValue()
        );
        // 2. Show the result in the big text area
        feedbackArea.setText(result);
    }

    @FXML
    void onCancelClick() {
        boolean success = cancelVisit(categoryBox.getValue(), siteBox.getValue(), idField.getText());

        if (success) {
            feedbackArea.setText("Success: Visit for visitor " + idField.getText() + " has been cancelled.");
        } else {
            feedbackArea.setText("Error: Could not cancel. Check Category, Site, and ID.");
        }
    }

    @FXML
    void onExitClick() {
        // This is real logic: It shuts down the Java program.
        System.exit(0);
    }

    @FXML
    void onSearchClick() {
        // 1. Get the list from your logic
        java.util.LinkedList<Visit> found = searchVisitor(searchField.getText(), searchField.getText());

        // 2. Clear table and add new data
        resultsTable.getItems().clear();
        resultsTable.getItems().addAll(found);

        // 3. User feedback
        if(found.isEmpty()) feedbackArea.setText("Search: No visitors found.");
        else feedbackArea.setText("Search: Found " + found.size() + " records.");
    }

    @FXML
    void onViewVisitsClick() {
        String cat = viewCategoryBox.getValue();
        if (cat == null) {
            feedbackArea.setText("Error: Please select a Category to filter.");
            return;
        }

        // 1. Call your logic method (It handles sorting & filtering now)
        LinkedList<Visit> data = viewVisits(cat, ascendingCheck.isSelected());

        // 2. Give the list to the Table
        resultsTable.getItems().setAll(data);
        feedbackArea.setText("Showing " + data.size() + " visits for " + cat);
    }

    @FXML
    void onRecentClick() {
        // 1. Get the list from your existing method
        java.util.LinkedList<Visit> recent = getRecentVisits();

        // 2. Update Table
        resultsTable.getItems().setAll(recent);
        feedbackArea.setText("Showing " + recent.size() + " most recent bookings.");
    }

    @FXML
    void onTotalsClick() {
        String report = displayTotals();
        feedbackArea.setText(report);
    }

    @FXML
    void onSummaryClick() {
        String report = generateSummaryReport();
        feedbackArea.setText(report);
    }

}
