package org.example.repository;

import org.example.domain.Trip;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TripRepositoryFilterByDestinationAndTime implements RepositoryFilter<Trip> {
    private String destination;
    private LocalDateTime begin;
    private LocalDateTime end;

    public TripRepositoryFilterByDestinationAndTime(String destination, LocalDateTime begin, LocalDateTime end) {
        this.destination = destination;
        this.begin = begin;
        this.end = end;
    }

    public String getSql() {
        return "WHERE destination LIKE '" + destination + "%' AND \"when\" >= '" + Timestamp.valueOf(begin) + "'" + " AND \"when\" <= '" + Timestamp.valueOf(end) + "'";
    }
}
