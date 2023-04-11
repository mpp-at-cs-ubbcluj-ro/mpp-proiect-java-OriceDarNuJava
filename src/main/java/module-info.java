module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens com.example.mpp to javafx.fxml;
    exports com.example.mpp;
    exports com.example.mpp.domain;
}