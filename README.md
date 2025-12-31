# Heritage Site Management System

A Java-based application developed for the **CSE202 Data Structures and Algorithms** course at the University of Kurdistan Hewlêr (UKH).

## 👥 Authors
* **Aran Razwan Khalid** (Computer Engineering)
* **Rami Ali** (Software Engineering)

---

## 📖 Project Purpose
The aim of this project is to simulate the management of heritage sites and visitor bookings using **pure in-memory data structures**.
* **No Databases:** All data is handled during runtime.
* **Strict Scope:** The implementation strictly adheres to data structures and algorithms covered in the CSE202 curriculum.

## 🚀 System Features
The system provides a Graphical User Interface (GUI) to handle the following operations:

* **Scheduling:** Book visits with input validation (ID check, Iraqi phone format, date limits) and traffic control simulation.
* **Management:**
    * **Cancel Visits:** Safe deletion of records.
    * **Search:** Find visitors by unique ID or Name.
    * **Sorting:** View visits sorted by date.
* **Analytics:**
    * **Live Totals:** Display total visit counts per category and site.
    * **Summary Report:** auto-calculate the most visited category and site.
    * **Recent Activity:** View the most recently scheduled visits (LIFO).

## 🛠️ Technologies Used
* **Language:** Java (JDK 23)
* **UI Framework:** JavaFX (FXML)
* **IDE:** IntelliJ IDEA

## 📊 Data Structures Implemented
The project demonstrates the practical application of the following structures:

### 1. LinkedList
* **Usage:** Stores the main list of all `Visit` records and `HeritageSite` objects.
* **Rationale:** Chosen for its ability to handle dynamic resizing efficiently and support easy traversal using iterators, fitting the requirement for a flexible list of bookings.

### 2. Stack
* **Usage:** Tracks the "Recent Bookings" history.
* **Rationale:** Follows the **Last-In-First-Out (LIFO)** principle, making it the perfect structure to display the most recently added visits at the top of the list.

## 📂 Design & Architecture
* **MVC Pattern:** The system separates the User Interface (`main-view.fxml`) from the logic.
* **Backend Logic:** All data processing is handled in `HeritageController.java`. The UI communicates with backend methods without duplicating logic.

## 📝 Note to Grader regarding File Naming
Per assignment requirements for the solution file name:
* The **Entry Point** (Main Class) is named: **`src/.../Rami_Aran.java`**
* The **Core Logic** is located in: **`HeritageController.java`**

## ⚖️ Academic Integrity
AI tools were used transparently to assist with refining code readability, debugging specific errors, and clarifying logic explanations. All core algorithms, structural decisions, and implementation logic were designed and written by the project authors.