package com.example.mpp;

import com.example.mpp.domain.TripDto;
import com.example.mpp.domain.User;
import com.example.mpp.services.TripService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReservationController {
    private final User currentUser;
    private final TripService service;

    private ObservableList<TripDto> tableItems;

    @FXML
    public TextField destinationFilter;

    @FXML
    public DatePicker beginDateFilter;

    @FXML
    public DatePicker endDateFilter;

    @FXML
    public TableView<TripDto> tripTable;

    @FXML
    public TextField personNameTextBox;

    @FXML
    public TextField cnpTextBox;

    @FXML
    public TextField reservedTextBox;

    public ReservationController(User currentUser, TripService service) throws SQLException {
        this.currentUser = currentUser;
        this.service = service;
    }

    @FXML
    public void load() throws SQLException {
        this.tableItems = FXCollections.observableList(service.getAllTrips());

        TableColumn<TripDto, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TripDto, String> sourceCol = new TableColumn<>("Source");
        sourceCol.setMinWidth(100);
        sourceCol.setCellValueFactory(new PropertyValueFactory<>("source"));

        TableColumn<TripDto, String> destinationCol = new TableColumn<>("Destination");
        destinationCol.setMinWidth(100);
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));

        TableColumn<TripDto, LocalDateTime> whenCol = new TableColumn<>("When");
        whenCol.setMinWidth(100);
        whenCol.setCellValueFactory(new PropertyValueFactory<>("when"));

        TableColumn<TripDto, Integer> spacesCol = new TableColumn<>("Spaces");
        spacesCol.setMinWidth(100);
        spacesCol.setCellValueFactory(new PropertyValueFactory<>("spaces"));

        TableColumn<TripDto, Integer> reservedSpotsCol = new TableColumn<>("Reserved");
        reservedSpotsCol.setMinWidth(100);
        reservedSpotsCol.setCellValueFactory(new PropertyValueFactory<>("reservedSpots"));

        tripTable.getColumns().add(nameCol);
        tripTable.getColumns().add(sourceCol);
        tripTable.getColumns().add(destinationCol);
        tripTable.getColumns().add(whenCol);
        tripTable.getColumns().add(spacesCol);
        tripTable.getColumns().add(reservedSpotsCol);
        tripTable.setItems(this.tableItems);
    }

    @FXML
    public void onSearch() {

    }

    @FXML
    public void onInsert() {

    }
}
