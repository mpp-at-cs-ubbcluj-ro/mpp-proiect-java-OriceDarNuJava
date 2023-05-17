package org.example.proxies;

import org.example.domain.TripDto;
import org.example.exceptions.InvalidReservationException;
import org.example.interfaces.TripServiceInterface;
import org.example.requests.reservations.AddReservationRequest;
import org.example.requests.trips.GetAllTripsRequest;
import org.example.requests.trips.GetTripsFilteredRequest;
import org.example.responses.ErrorResponse;
import org.example.responses.SuccessResponse;
import org.example.responses.trips.GetAllTripsResponse;
import org.example.responses.trips.GetTripsFilteredResponse;
import org.example.utils.Observer;
import org.example.utils.Request;
import org.example.utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripServiceProxy implements TripServiceInterface {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final List<Observer> observers;

    public TripServiceProxy(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.observers = new ArrayList<>();
    }

    @Override
    public List<TripDto> getAllTrips() throws SQLException {
        this.sendRequest(new GetAllTripsRequest());
        Response response = this.getResponse();
        if (response instanceof GetAllTripsResponse getAllTripsResponse) {
            return getAllTripsResponse.getTripsDto();
        }
        return null;
    }

    @Override
    public List<TripDto> getTripsFiltered(String destination, LocalDateTime begin, LocalDateTime end) throws SQLException {
        this.sendRequest(new GetTripsFilteredRequest(destination, begin, end));
        Response response = this.getResponse();
        if (response instanceof GetTripsFilteredResponse getTripsFilteredResponse) {
            return getTripsFilteredResponse.getTripsDto();
        }
        return null;
    }

    @Override
    public void addReservation(String name, String cnp, String reserved, TripDto trip, Integer userId) throws SQLException {
        this.sendRequest(new AddReservationRequest(name, cnp, reserved, userId, trip));
        Response response = this.getResponse();
        if (response instanceof SuccessResponse) {
            return;
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new InvalidReservationException(errorResponse.getMessage());
        }
    }

    private void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error in sending request");
        }
    }

    private Response getResponse() {
        Response response = null;
        try {
            response = (Response) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in reading response {}" + e.getMessage());
        }
        return response;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }
}
