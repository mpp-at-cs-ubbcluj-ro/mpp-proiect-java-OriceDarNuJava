package org.example;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.domain.Reservation;
import org.example.repository.*;
import org.example.utils.PostgresJDBC;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import org.example.domain.Trip;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/java/org/example/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Logger logger = LogManager.getLogger();
        Connection connection = PostgresJDBC.getConnection(properties);
        logger.info("Connection established");

        Repository<Trip> tripRepository = new TripRepositoryDB(connection, logger);
        RepositoryFilter<Trip> filter = new TripRepositoryFilterByDestinationAndTime("Bucuresti", "Brasov", LocalDateTime.now(), LocalDateTime.of(2023, 12, 31, 23, 59, 59));
        List<Trip> l = tripRepository.get(filter);
        l.forEach(System.out::println);
        Repository<Reservation> reservationRepository = new ReservationRepositoryDB(connection, logger);
        Reservation reservation = new Reservation(1, 1, "Ion", 1, "1234567890123", 3);
        reservationRepository.push(reservation);
    }
}