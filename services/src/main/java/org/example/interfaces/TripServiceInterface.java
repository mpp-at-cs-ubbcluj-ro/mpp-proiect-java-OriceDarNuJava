package org.example.interfaces;

import org.example.domain.TripDto;
import org.example.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface TripServiceInterface extends Observable {
    List<TripDto> getAllTrips() throws SQLException;

    List<TripDto> getTripsFiltered(String destination, LocalDateTime begin, LocalDateTime end) throws SQLException;

    void addReservation(String name, String cnp, String reserved, TripDto trip, Integer userId) throws SQLException;
}
