module com.example.mpp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires annotations;

    opens com.example.mpp to javafx.fxml;
    exports com.example.mpp;
    exports com.example.mpp.domain;
}