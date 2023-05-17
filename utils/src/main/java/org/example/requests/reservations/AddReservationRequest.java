package org.example.requests.reservations;

import org.example.domain.TripDto;
import org.example.utils.Request;

public class AddReservationRequest implements Request {
    private final String name;
    private final String cnp;
    private final String reserved;
    private final Integer userId;
    private final TripDto trip;

    public AddReservationRequest(String name, String cnp, String reserved, Integer userId, TripDto trip) {
        this.name = name;
        this.cnp = cnp;
        this.reserved = reserved;
        this.userId = userId;
        this.trip = trip;
    }

    public String getName() {
        return name;
    }

    public String getCnp() {
        return cnp;
    }

    public String getReserved() {
        return reserved;
    }

    public Integer getUserId() {
        return userId;
    }

    public TripDto getTrip() {
        return this.trip;
    }
}
