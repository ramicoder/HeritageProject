# Acceptance Test Plan - Heritage Site Management System
**Project Phase:** Phase 4 (Testing & Validation)
**Testers:** Rami & Aran
**Date:** December 30, 2025

---

## 1. Input Validation (Security Gates)
*Goal: Ensure the system rejects invalid data before processing.*

| Test ID | Description | Input Data | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- |
| **VAL-01** | **Empty Fields Check** | Leave all fields blank. Click "Schedule". | Error: "All fields are required." | ✅ PASS |
| **VAL-02** | **Invalid ID Format** | ID: `A123` (Letters). Other fields valid. | Error: "ID must be numeric." | ✅ PASS |
| **VAL-03** | **Phone Length Short** | Phone: `0750123` (Too short). | Error: "Phone number must be exactly 11 digits..." | ✅ PASS |
| **VAL-04** | **Phone Wrong Prefix** | Phone: `0900123...` (Not 07xx). | Error: "Phone number must... start with '07'." | ✅ PASS |
| **VAL-05** | **Past Date Check** | Date: Select yesterday's date. | Error: "You cannot book a visit in the past." | ✅ PASS |
| **VAL-06** | **Successful Input** | Name: `Aran` <br> ID: `1001` <br> Phone: `07501234567` <br> Date: Tomorrow. | Success: "Scheduled: Aran to [Site]" <br> Input boxes clear automatically. | ✅ PASS |

---

## 2. Business Logic (Core Rules)
*Goal: Verify specific requirements (ID Theft, Rewards, Duplicates).*

| Test ID | Description | Input Data | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- |
| **LOG-01** | **ID Consistency (Pass)** | Use `1001` (from VAL-06). <br> Name: `Aran`. <br> Phone: `07501234567`. | Success: Visit is scheduled. (Same person returning). | ✅ PASS |
| **LOG-02** | **ID Theft Protection** | Use `1001` (Belongs to Aran). <br> Name: `Rami` (Different name). | Error: "ID 1001 belongs to Aran. Details do not match." | ✅ PASS |
| **LOG-03** | **Double Booking Prevention** | **Prerequisite:** LOG-01 passed. <br> Try to book `1001` for same site/date. | Error: "This visit is already scheduled for this date." | ✅ PASS |
| **LOG-04** | **Free Tour Reward** | Schedule ID `2002` (Visit 1). <br> Schedule ID `2002` (Visit 2). <br> Schedule ID `2002` (Visit 3). | Success Message includes: <br> `*** FREE GUIDED TOUR QUALIFIED ***` <br> `Confirmation Code: [Random Number]` | ✅ PASS |

---

## 3. Data Management (UI & Search)
*Goal: Verify data integrity and retrieval.*

| Test ID | Description | Input Data | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- |
| **DAT-01** | **View List (Filtered)** | Select Category: "Historical". <br> Click "View List". | Table shows only Historical sites. Feedback: "Showing X visits for Historical". | ✅ PASS |
| **DAT-02** | **Sorting (Ascending)** | Check "Ascending" box. <br> Click "View List". | Table refreshes. Dates are sorted Oldest $\rightarrow$ Newest. | ✅ PASS |
| **DAT-03** | **Search by ID** | Type `1001` in Search Box. <br> Click "Search". | Table shows only Aran's visits. | ✅ PASS |
| **DAT-04** | **Search by Name** | Type `Aran` in Search Box. <br> Click "Search". | Table shows only Aran's visits. | ✅ PASS |
| **DAT-05** | **Recent Bookings** | Click "Recent Bookings". | Table shows last 10 visits. Most recent at the top. | ✅ PASS |
| **DAT-06** | **Cancellation Sync** | Step 1: Cancel ID `1001`. <br> Step 2: Click "Recent Bookings". | Step 1: "Success: Visit cancelled." <br> Step 2: Visit is GONE from table. | ✅ PASS |
| **DAT-07** | **System Totals** | Click "System Totals". | Feedback area prints list of Categories and Sites with visit counts. | ✅ PASS |