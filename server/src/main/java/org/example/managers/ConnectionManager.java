package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.TripDto;
import org.example.exceptions.InvalidReservationException;
import org.example.interfaces.TripServiceInterface;
import org.example.interfaces.UserServiceInterface;
import org.example.requests.reservations.AddReservationRequest;
import org.example.requests.trips.GetAllTripsRequest;
import org.example.requests.trips.GetTripsFilteredRequest;
import org.example.requests.user.GetUserRequest;
import org.example.responses.ErrorResponse;
import org.example.responses.NotFoundResponse;
import org.example.responses.SuccessResponse;
import org.example.responses.trips.GetAllTripsResponse;
import org.example.responses.trips.GetTripsFilteredResponse;
import org.example.responses.user.SuccessAuthenticationResponse;
import org.example.utils.*;
import org.example.domain.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager implements Runnable {
    private final Socket socket;
    private final Socket updateChannel;
    private final TripServiceInterface tripService;
    private final UserServiceInterface userService;
    private final Logger logger;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private volatile boolean isConnected;

    public ConnectionManager(Socket socket, Socket updateChannel, TripServiceInterface tripService, UserServiceInterface userService) throws IOException {
        this.socket = socket;
        this.updateChannel = updateChannel;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.tripService = tripService;
        this.userService = userService;
        this.logger = LogManager.getLogger();
    }

    private Response handleGetUserRequest(GetUserRequest request) {
        logger.info("Handling get user request");
        try {
            User authenticated = this.userService.getByUsername(request.getUsername());
            this.logger.info("Found user: " + authenticated);
            if (authenticated == null) {
                return new NotFoundResponse();
            }
            this.tripService.addObserver(new ClientObserver(this.updateChannel));
            return new SuccessAuthenticationResponse(authenticated);
        } catch (SQLException | IOException e) {
            this.logger.info("Error in getting user  {}", e.getMessage());
        }
        return new NotFoundResponse();
    }

    private Response handleGetAllTripsRequest() {
        this.logger.info("Handling get all trips request");
        try {
            List<TripDto> trips = this.tripService.getAllTrips();
            this.logger.info("Found all trips");
            return new GetAllTripsResponse(trips);
        } catch (SQLException e) {
            this.logger.info("Error in getting all trips  {}", e.getMessage());
        }
        return new NotFoundResponse();
    }

    private Response handleGetTripsFilteredRequest(GetTripsFilteredRequest request) {
        this.logger.info("Handling get trips filtered request");
        try {
            List<TripDto> trips = this.tripService.getTripsFiltered(request.getDestination(), request.getBegin(), request.getEnd());
            this.logger.info("Found trips filtered");
            return new GetTripsFilteredResponse(trips);
        } catch (SQLException e) {
            this.logger.info("Error in getting trips, ", e.getMessage());
        }
        return new NotFoundResponse();
    }

    private Response handleAddReservationRequest(AddReservationRequest request) {
        this.logger.info("Handling add reservation request");
        try {
            this.tripService.addReservation(request.getName(), request.getCnp(), request.getReserved(), request.getTrip(), request.getUserId());
            this.tripService.notifyObservers();
            return new SuccessResponse();
        } catch (SQLException | InvalidReservationException e) {
            this.logger.info("Could not add reservation, {}", e.getMessage());
            return new ErrorResponse(e.getMessage());
        } catch (IOException e) {
            return new SuccessResponse();
        }
    }

    @Override
    public void run() {
        isConnected = true;
        while (isConnected) {
            try {
                Request request = (Request) this.in.readObject();
                Response response = this.handleRequest(request);
                if (response != null) {
                    this.sendResponse(response, this.out);
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if (request instanceof GetUserRequest) {
            response = this.handleGetUserRequest((GetUserRequest) request);
        }
        if (request instanceof GetAllTripsRequest) {
            response = this.handleGetAllTripsRequest();
        }
        if (request instanceof GetTripsFilteredRequest) {
            response = this.handleGetTripsFilteredRequest((GetTripsFilteredRequest) request);
        }
        if (request instanceof AddReservationRequest) {
            response = this.handleAddReservationRequest((AddReservationRequest) request);
        }
        return response;
    }

    private void sendResponse(Response response, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException e) {
            this.logger.info("Error in sending response{}", e.getMessage());
        }

    }
}
