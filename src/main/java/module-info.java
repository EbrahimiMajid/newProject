module Hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires lombok;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;

    requires org.hibernate.orm.core;

    opens base;
    opens entity;
    opens Hello to javafx.fxml;
    exports Hello;


}
