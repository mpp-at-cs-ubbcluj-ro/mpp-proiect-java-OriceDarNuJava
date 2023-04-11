package com.example.mpp.domain;

import java.time.LocalDateTime;

public class Trip extends Entity {
    private String name;
    private String source;
    private String destination;
    private LocalDateTime when;
    private Integer spaces;

    public Trip(Integer id, String name, String source, String destination, LocalDateTime when, Integer spaces) {
        super(id);
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.when = when;
        this.spaces = spaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }
}
