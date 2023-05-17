package org.example.utils;

import java.io.IOException;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers() throws IOException;
}
