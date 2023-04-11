package com.example.mpp.domain;

import java.time.LocalDateTime;

public class TripDto {
    private String name;
    private String source;
    private String destination;
    private LocalDateTime when;
    private Integer spaces;
    private Integer reservedSpots;

    public TripDto(Trip trip, Integer reservedSpots) {
        this.name = trip.getName();
        this.source = trip.getSource();
        this.destination = trip.getDestination();
        this.when = trip.getWhen();
        this.spaces = trip.getSpaces();
        this.reservedSpots = reservedSpots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }
}
