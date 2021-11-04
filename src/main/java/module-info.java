module com.pbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Model to javafx.fxml;
    exports Model;
}