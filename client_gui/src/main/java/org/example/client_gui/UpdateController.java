package org.example.client_gui;

import javafx.application.Platform;
import org.example.responses.observers.UpdateNotification;
import org.example.utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class UpdateController implements Runnable {
    private final Socket updateChannel;
    private final ObjectInputStream in;
    private final ReservationController controller;

    public UpdateController(Socket updateChannel, ReservationController controller) throws IOException {
        this.updateChannel = updateChannel;
        this.controller = controller;
        this.in = new ObjectInputStream(updateChannel.getInputStream());
    }

    @Override
    public void run() {
        while (updateChannel.isConnected()) {
            try {
                Response notification = (Response) in.readObject();
                if (notification instanceof UpdateNotification) {
                    Platform.runLater(this.controller::update);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
