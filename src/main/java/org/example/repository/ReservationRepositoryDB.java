package org.example.repository;

import org.apache.logging.log4j.Logger;
import org.example.domain.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ReservationRepositoryDB implements Repository<Reservation> {
    private final Connection connection;
    private final Logger logger;

    public ReservationRepositoryDB(Connection connection, Logger logger) {
        this.connection = connection;
        this.logger = logger;
    }

    @Override
    public void push(Reservation reservation) throws SQLException {
        logger.info("Trip added");
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into labmpppb6.reservations (person_name, user_id, personal_identification, reserved_spots) values ('" + reservation.getPersonName() + "', " + reservation.getUserId() + ", '" + reservation.getPersonalIdentification() + "', " + reservation.getReservedSpots() + ")");
        statement.close();
    }

    @Override
    public List<Reservation> get(Integer id) {
        return null;
    }

    @Override
    public List<Reservation> get(RepositoryFilter<Reservation> filter) {
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        return null;
    }

    @Override
    public void update(Reservation oldValue, Reservation newValue) {

    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int delete(RepositoryFilter<Reservation> filter) {
        return 0;
    }
}
