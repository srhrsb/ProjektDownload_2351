module com.brh.projektdownload_2351 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.brh.projektdownload_2351 to javafx.fxml;
    exports com.brh.projektdownload_2351;
}