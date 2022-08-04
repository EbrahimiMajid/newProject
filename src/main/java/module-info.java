module Hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires lombok;
    requires java.sql;


    opens Hello to javafx.fxml;
    exports Hello;
}