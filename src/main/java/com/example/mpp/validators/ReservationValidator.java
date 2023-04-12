package com.example.mpp.validators;

import com.example.mpp.domain.Reservation;
import com.example.mpp.domain.TripDto;
import com.example.mpp.exceptions.InvalidReservationException;

import java.util.regex.Pattern;

public class ReservationValidator {
    private final static ReservationValidator instance = new ReservationValidator();

    private ReservationValidator() {}

    public static ReservationValidator getInstance() {
        return instance;
    }

    public void validate(Reservation reservation, TripDto trip) {
        try {
            assert(reservation.getReservedSpots() + trip.getReservedSpots() <= trip.getSpaces());
            assert(Pattern.compile("[1-6][0-9]{12}").matcher(reservation.getPersonalIdentification()).matches());

            assert(Pattern.compile("[A-Z][a-z]+( [A-Z][a-z]+)+").matcher(reservation.getPersonName()).matches());
        } catch (Exception e) {
            throw new InvalidReservationException("Invalid data!" + e.getMessage());
        }

    }
}
