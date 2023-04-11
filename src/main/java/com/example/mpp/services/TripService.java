package com.example.mpp.services;

import com.example.mpp.domain.Reservation;
import com.example.mpp.domain.Trip;
import com.example.mpp.domain.TripDto;
import com.example.mpp.repository.Repository;
import com.example.mpp.repository.RepositoryFilter;
import com.example.mpp.repository.ReservationRepositoryFilterByTrip;
import com.example.mpp.repository.TripRepositoryFilterByDestinationAndTime;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripService {
    private final Repository<Trip> tripRepository;
    private final Repository<Reservation> reservationRepository;

    public TripService(Repository<Trip> tripRepository, Repository<Reservation> reservationRepository) {
        this.tripRepository = tripRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<TripDto> getAllTrips() throws SQLException {
        List<Trip> trips = this.tripRepository.getAll();

        return getTripDtos(trips);
    }

    public List<TripDto> getTripsFiltered(String destination, LocalDateTime begin, LocalDateTime end) throws SQLException {
        RepositoryFilter<Trip> filter = new TripRepositoryFilterByDestinationAndTime(destination, begin, end);
        List<Trip> trips = this.tripRepository.get(filter);

        return getTripDtos(trips);
    }

    public void addReservation(String name, String cnp, String reserved, Integer tripId, Integer userId) throws SQLException {
        Reservation reservation = new Reservation(0, tripId, name, userId, cnp, Integer.parseInt(reserved));
        this.reservationRepository.push(reservation);
    }

    private List<TripDto> getTripDtos(List<Trip> trips) throws SQLException {
        List<TripDto> result = new ArrayList<>();
        for (Trip trip : trips) {
            int spots = 0;
            RepositoryFilter<Reservation> filter = new ReservationRepositoryFilterByTrip(trip);
            List<Reservation> reservations = this.reservationRepository.get(filter);

            for (Reservation reservation : reservations) {
                spots += reservation.getReservedSpots();
            }

            result.add(new TripDto(trip, spots));
        }

        return result;
    }
}
