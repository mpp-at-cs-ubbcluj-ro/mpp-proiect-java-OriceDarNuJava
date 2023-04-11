package com.example.mpp.repository;

import com.example.mpp.domain.Trip;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TripRepositoryFilterByDestinationAndTime implements RepositoryFilter<Trip> {
    private String source;
    private String destination;
    private LocalDateTime begin;
    private LocalDateTime end;

    public TripRepositoryFilterByDestinationAndTime(String source, String destination, LocalDateTime begin, LocalDateTime end) {
        this.source = source;
        this.destination = destination;
        this.begin = begin;
        this.end = end;
    }

    public String getSql() {
        return "WHERE source LIKE '" + source + "' AND destination LIKE '" + destination + "' AND \"when\" >= '" + Timestamp.valueOf(begin) + "'" + " AND \"when\" <= '" + Timestamp.valueOf(end) + "'";
    }
}
