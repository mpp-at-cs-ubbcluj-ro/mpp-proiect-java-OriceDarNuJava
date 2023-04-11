package com.example.mpp;

import com.example.mpp.domain.User;
import com.example.mpp.repository.Repository;
import com.example.mpp.repository.ReservationRepositoryDB;
import com.example.mpp.repository.TripRepositoryDB;
import com.example.mpp.repository.UserRepositoryDB;
import com.example.mpp.services.TripService;
import com.example.mpp.utils.PostgresJDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/java/com/example/mpp/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logger logger = LogManager.getLogger();
        Connection connection = PostgresJDBC.getConnection(properties);
        logger.info("Connection established");

        TripService service = new TripService(new TripRepositoryDB(connection, logger), new ReservationRepositoryDB(connection, logger));
        Repository<User> userRepository = new UserRepositoryDB(connection, logger);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        LoginController controller = new LoginController(userRepository, service);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 810, 577);
        stage.setTitle("Reservations");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}