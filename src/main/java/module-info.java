module com.example.projectgraphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.projectgraphic to javafx.fxml;
    exports com.example.projectgraphic;
}