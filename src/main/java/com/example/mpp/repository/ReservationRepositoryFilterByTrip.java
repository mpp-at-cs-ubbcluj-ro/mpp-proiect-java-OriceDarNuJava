package com.example.mpp.repository;

import com.example.mpp.domain.Reservation;
import com.example.mpp.domain.Trip;

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
