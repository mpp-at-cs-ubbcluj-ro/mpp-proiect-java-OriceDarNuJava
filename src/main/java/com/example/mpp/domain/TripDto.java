package com.example.mpp.domain;

import java.time.LocalDateTime;

public class TripDto {
    private final String name;
    private final String source;
    private final String destination;
    private final LocalDateTime when;
    private final Integer spaces;
    private final Integer reservedSpots;

    public TripDto(Trip trip, Integer reservedSpots) {
        this.name = trip.getName();
        this.source = trip.getSource();
        this.destination = trip.getDestination();
        this.when = trip.getWhen();
        this.spaces = trip.getSpaces();
        this.reservedSpots = reservedSpots;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public Integer getSpaces() {
        return spaces;
    }
}
