package org.example.requests.trips;

import org.example.utils.Request;

import java.time.LocalDateTime;

public class GetTripsFilteredRequest implements Request {
    private String destination;
    private LocalDateTime begin;
    private LocalDateTime end;

    public GetTripsFilteredRequest(String destination, LocalDateTime begin, LocalDateTime end) {
        this.destination = destination;
        this.begin = begin;
        this.end = end;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
