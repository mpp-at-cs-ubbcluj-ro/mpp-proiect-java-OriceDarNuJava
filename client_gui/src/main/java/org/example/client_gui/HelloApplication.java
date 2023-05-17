package org.example.client_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.interfaces.TripServiceInterface;
import org.example.interfaces.UserServiceInterface;
import org.example.proxies.TripServiceProxy;
import org.example.proxies.UserServiceProxy;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Socket updateChannel = new Socket("localhost", 1235);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("/Users/antoniuficard/IdeaProjects/untitled1/src/main/java/org/example/mpp/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logger logger = LogManager.getLogger();
        logger.info("Starting application!");
        TripServiceInterface service = new TripServiceProxy(socket, in, out);
        UserServiceInterface userService = new UserServiceProxy(socket, in, out);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Login.fxml"));
        LoginController controller = new LoginController(userService, service, updateChannel);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 810, 577);
        stage.setTitle("Reservations");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
