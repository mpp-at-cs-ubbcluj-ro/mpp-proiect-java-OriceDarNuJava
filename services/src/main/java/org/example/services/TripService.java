package org.example.services;

import org.example.domain.Reservation;
import org.example.domain.Trip;
import org.example.domain.TripDto;
import org.example.exceptions.InvalidReservationException;
import org.example.interfaces.TripServiceInterface;
import org.example.repository.Repository;
import org.example.repository.RepositoryFilter;
import org.example.repository.ReservationRepositoryFilterByTrip;
import org.example.repository.TripRepositoryFilterByDestinationAndTime;
import org.example.utils.Observer;
import org.example.validators.ReservationValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripService implements TripServiceInterface {
    private final Repository<Trip> tripRepository;
    private final Repository<Reservation> reservationRepository;
    private final List<Observer> observers = new ArrayList<>();

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

    public void addReservation(String name, String cnp, String reserved, TripDto tripDto, Integer userId) throws SQLException, InvalidReservationException {
        Reservation reservation = new Reservation(0, tripDto.getTripId(), name, userId, cnp, Integer.parseInt(reserved));
        List<Trip> trips = this.tripRepository.get(tripDto.getTripId());
        List<TripDto> db = this.getTripDtos(trips);
        ReservationValidator.getInstance().validate(reservation, db.get(0));

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
