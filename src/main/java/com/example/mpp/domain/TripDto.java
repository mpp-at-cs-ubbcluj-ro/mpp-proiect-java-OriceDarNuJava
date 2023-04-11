package com.example.mpp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TripDto {
    private final Integer tripId;
    private final String name;
    private final String source;
    private final String destination;
    private final String when;
    private final Integer spaces;
    private Integer reservedSpots;

    public TripDto(Trip trip, Integer reservedSpots) {
        this.tripId = trip.getId();
        this.name = trip.getName();
        this.source = trip.getSource();
        this.destination = trip.getDestination();
        this.when = trip.getWhen().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.spaces = trip.getSpaces();
        this.reservedSpots = reservedSpots;
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

    public String getWhen() {
        return when;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }
}
