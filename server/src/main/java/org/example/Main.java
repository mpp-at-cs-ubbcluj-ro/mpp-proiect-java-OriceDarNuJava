package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.interfaces.TripServiceInterface;
import org.example.managers.ConnectionManager;
import org.example.repository.ReservationRepositoryDB;
import org.example.repository.TripRepositoryDB;
import org.example.repository.UserRepositoryDB;
import org.example.services.TripService;
import org.example.services.UserService;
import org.example.utils.PostgresJDBC;

import java.io.IOException;
import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        logger.info("Starting server!");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("/Users/antoniuficard/IdeaProjects/untitled1/src/main/java/org/example/mpp/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection connection = PostgresJDBC.getConnection(properties);
        logger.info("Connection established");

        UserService userService = new UserService(new UserRepositoryDB(connection, logger));
        TripService tripService = new TripService(new TripRepositoryDB(connection, logger), new ReservationRepositoryDB(connection, logger));

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            ServerSocket updateSocket = new ServerSocket(1235);
            while (true) {
                Socket socket = serverSocket.accept();
                Socket updateChannel = updateSocket.accept();
                logger.info("Got a new client");
                Thread thread = new Thread(new ConnectionManager(socket, updateChannel, tripService, userService));
                thread.start();
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}