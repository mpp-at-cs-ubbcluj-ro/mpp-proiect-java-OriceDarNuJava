package org.example.client_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.domain.User;
import org.example.interfaces.TripServiceInterface;
import org.example.interfaces.UserServiceInterface;
import org.example.services.TripService;
import org.example.services.UserService;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class LoginController {
    private final UserServiceInterface userService;
    private final TripServiceInterface service;
    private final Socket updateChannel;
    @FXML
    public TextField usernameTextField;

    @FXML
    public Label errorMessage;

    public LoginController(UserServiceInterface userService, TripServiceInterface service, Socket updateChannel) {
        this.userService = userService;
        this.service = service;
        this.updateChannel = updateChannel;
    }

    @FXML
    public void onLoginClick() throws IOException, SQLException {
        User authenticated = this.userService.getByUsername(this.usernameTextField.getText());

        if (null == authenticated) {
            this.errorMessage.setText("Invalid username!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Reservation.fxml"));
        ReservationController controller = new ReservationController(authenticated, service, updateChannel);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1140, 740);
        Stage stage = new Stage();
        stage.setTitle("Reservations");
        stage.setScene(scene);
        stage.show();
        controller.load();
        Stage currentWindow = (Stage) usernameTextField.getScene().getWindow();
        currentWindow.close();
    }
}