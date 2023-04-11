package com.example.mpp.repository;

import com.example.mpp.domain.Trip;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripRepositoryDB implements Repository<Trip> {
    private final Connection connection;
    private final Logger logger;

    public TripRepositoryDB(Connection connection, Logger logger) {
        this.connection = connection;
        this.logger = logger;
    }

    @Override
    public void push(Trip trip) {
    }

    @Override
    public List<Trip> get(Integer id) {
        return null;
    }

    @Override
    public List<Trip> get(RepositoryFilter<Trip> filter) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM labmpppb6.trips " + filter.getSql();
        return getTrips(statement, sql);
    }

    @Override
    public List<Trip> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM labmpppb6.trips;";
        return getTrips(statement, sql);
    }

    private List<Trip> getTrips(Statement statement, String sql) throws SQLException {
        ResultSet rs = statement.executeQuery(sql);

        List<Trip> trips = new ArrayList<>();

        while(rs.next()) {
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String source = rs.getString("source");
            String destination = rs.getString("destination");
            Timestamp timestamp = rs.getTimestamp("when");
            LocalDateTime when = LocalDateTime.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDay(), timestamp.getHours(), timestamp.getMinutes(), timestamp.getSeconds());
            Integer spaces = rs.getInt("spaces");
            Trip trip = new Trip(id, name, source, destination, when, spaces);

            trips.add(trip);
        }
        return trips;
    }

    @Override
    public void update(Trip oldValue, Trip newValue) {

    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int delete(RepositoryFilter<Trip> filter) {
        return 0;
    }
}
