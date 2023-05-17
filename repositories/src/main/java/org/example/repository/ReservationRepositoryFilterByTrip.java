package org.example.repository;

import org.example.domain.Reservation;
import org.example.domain.Trip;

public class ReservationRepositoryFilterByTrip implements RepositoryFilter<Reservation> {
    private final Trip trip;

    public ReservationRepositoryFilterByTrip(Trip trip) {
        this.trip = trip;
    }


    @Override
    public String getSql() {
        return "where trip_id = " + this.trip.getId();
    }
}
