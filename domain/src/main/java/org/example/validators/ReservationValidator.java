package org.example.validators;

import org.example.domain.Reservation;
import org.example.domain.TripDto;
import org.example.exceptions.InvalidReservationException;

import java.util.regex.Pattern;

public class ReservationValidator {
    private final static ReservationValidator instance = new ReservationValidator();

    private ReservationValidator() {
    }

    public static ReservationValidator getInstance() {
        return instance;
    }

    public void validate(Reservation reservation, TripDto trip) {
        if (reservation.getReservedSpots() + trip.getReservedSpots() > trip.getSpaces()) {
            throw new InvalidReservationException("Invalid spots!");
        }
        if (!Pattern.compile("[1-6][0-9]{12}").matcher(reservation.getPersonalIdentification()).matches()) {
            throw new InvalidReservationException("Invalid CNP!");
        }

        if (!Pattern.compile("[A-Z][a-z]+( [A-Z][a-z]+)+").matcher(reservation.getPersonName()).matches()) {
            throw new InvalidReservationException("Invalid name!");
        }
    }
}
