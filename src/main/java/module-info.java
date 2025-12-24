module com.ramiaran.heritagesitesystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens com.ramiaran.heritagesitesystem to javafx.fxml;
    exports com.ramiaran.heritagesitesystem;
}