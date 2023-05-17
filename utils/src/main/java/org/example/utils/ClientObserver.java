package org.example.utils;

import org.example.responses.observers.UpdateNotification;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientObserver implements Observer {
    private Socket socket;
    private ObjectOutputStream out;

    public ClientObserver(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void update() throws IOException {
        this.out.writeObject(new UpdateNotification());
    }
}
