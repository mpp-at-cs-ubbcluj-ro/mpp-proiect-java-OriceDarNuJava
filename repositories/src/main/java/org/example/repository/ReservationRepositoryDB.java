package org.example.repository;

import org.example.domain.Reservation;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        statement.executeUpdate("insert into labmpppb6.reservations (person_name, user_id, personal_identification, reserved_spots, trip_id) values ('" + reservation.getPersonName() + "', " + reservation.getUserId() + ", '" + reservation.getPersonalIdentification() + "', " + reservation.getReservedSpots() + ", " + reservation.getTripId() + ")");
        statement.close();
    }

    @Override
    public List<Reservation> get(Integer id) {
        return null;
    }

    @Override
    public List<Reservation> get(RepositoryFilter<Reservation> filter) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM labmpppb6.reservations " + filter.getSql();
        ResultSet rs = statement.executeQuery(sql);

        List<Reservation> reservations = new ArrayList<>();

        while(rs.next()) {
            Integer id = rs.getInt("id");
            String personName = rs.getString("person_name");
            int userId = rs.getInt("user_id");
            String personalIdentification = rs.getString("personal_identification");
            int reservedSpots = rs.getInt("reserved_spots");
            int tripId = rs.getInt("trip_id");
            Reservation reservation = new Reservation(id, tripId, personName, userId, personalIdentification, reservedSpots);

            reservations.add(reservation);
        }
        return reservations;
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
