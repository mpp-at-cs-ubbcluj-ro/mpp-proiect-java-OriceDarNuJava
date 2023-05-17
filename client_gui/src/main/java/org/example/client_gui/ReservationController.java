package org.example.client_gui;

import org.example.domain.TripDto;
import org.example.domain.User;
import org.example.interfaces.TripServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReservationController {
    private final User currentUser;
    private final TripServiceInterface service;
    private final Socket updateChannel;

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

    private String lastFilteredDestination = null;
    private LocalDateTime lastFilteredBegin = null;
    private LocalDateTime lastFilteredEnd = null;
    private BlockingQueue<Integer> threadMessages;

    public ReservationController(User currentUser, TripServiceInterface service, Socket updateChannel) throws SQLException {
        this.currentUser = currentUser;
        this.service = service;
        this.updateChannel = updateChannel;
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

        TableColumn<TripDto, String> whenCol = new TableColumn<>("When");
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

        this.threadMessages = new ArrayBlockingQueue<>(1);
        try {
            Thread updateThread = new Thread(new UpdateController(updateChannel, this));
            updateThread.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start update thread");
        }
    }

    @FXML
    public void onSearch() throws SQLException {
        LocalDateTime begin = this.beginDateFilter.getValue().atTime(0, 0);
        LocalDateTime end = this.endDateFilter.getValue().atTime(0, 0);
        String destination = this.destinationFilter.getText();

        this.lastFilteredBegin = begin;
        this.lastFilteredEnd = end;
        this.lastFilteredDestination = destination;

        this.tableItems.clear();
        this.tableItems.addAll(this.service.getTripsFiltered(destination, begin, end));
    }

    @FXML
    public void onInsert() {
        TripDto trip = this.tripTable.getSelectionModel().getSelectedItem();
        String name = this.personNameTextBox.getText();
        String cnp = this.cnpTextBox.getText();
        String reserved = this.reservedTextBox.getText();

        try {
            this.service.addReservation(name, cnp, reserved, trip, this.currentUser.getId());
        } catch (Exception e) {
        }
    }

    public void update() {
        List<TripDto> trips = null;
        if (this.lastFilteredDestination == null || this.lastFilteredBegin == null || this.lastFilteredEnd == null) {
            try {
                trips = this.service.getAllTrips();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                trips = this.service.getTripsFiltered(this.lastFilteredDestination, this.lastFilteredBegin, this.lastFilteredEnd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (trips == null) {
            return;
        }
        this.tableItems.clear();
        this.tableItems.addAll(trips);
    }
}
