package com.ramiaran.heritagesitesystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*

 * PROJECT: UKH Heritage Management System
 * FILE:    Rami_Aran.java
 * DATE:    31/12/2025

 * TEAM MEMBERS & CONTRIBUTIONS:
 *
 * 1. Rami Ali
 * - Major: Software Engineering (SE S3)
 * - Contributions:
 * - Designed and implemented the User Interface (JavaFX/FXML).
 * - Implemented HeritageSite class logic.
 * - Developed Logic for Options 2, 4, 5, and 8.
 * - Handled FXML Controller methods and event linking.
 *
 * 2. Aran Razwan Khalid
 * - Major: Computer Engineering (CE)
 * - Contributions:
 * - Implemented the Visit class structure.
 * - Developed Logic for Options 1, 3, 6, 7, and 9.
 * - Handled core data processing and validation logic.

 */

public class Rami_Aran extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Rami_Aran.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Heritage Site");
        stage.setScene(scene);
        stage.show();
    }

}
