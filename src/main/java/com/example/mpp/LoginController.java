package com.example.mpp;

import com.example.mpp.domain.User;
import com.example.mpp.repository.Repository;
import com.example.mpp.repository.RepositoryFilter;
import com.example.mpp.repository.UserRepositoryFilterByUsername;
import com.example.mpp.services.TripService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginController {
    private final Repository<User> userRepository;
    private final TripService service;
    @FXML
    public TextField usernameTextField;

    @FXML
    public Label errorMessage;

    public LoginController(Repository<User> userRepository, TripService service) {
        this.userRepository = userRepository;
        this.service = service;
    }

    @FXML
    public void onLoginClick() throws IOException, SQLException {
        RepositoryFilter<User> filter = new UserRepositoryFilterByUsername(usernameTextField.getText());
        List<User> matchedUsers = this.userRepository.get(filter);

        if (matchedUsers.size() == 0) {
            this.errorMessage.setText("Invalid username!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Reservation.fxml"));
        ReservationController controller = new ReservationController(matchedUsers.get(0), service);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1140, 740);
        Stage stage = new Stage();
        stage.setTitle("Reservations");
        stage.setScene(scene);
        stage.show();
        Stage currentWindow = (Stage) usernameTextField.getScene().getWindow();
        currentWindow.close();
    }
}