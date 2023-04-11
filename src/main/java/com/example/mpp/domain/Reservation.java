package com.example.mpp.domain;

public class Reservation extends Entity {
    private Integer tripId;
    private String personName;
    private Integer userId;
    private String personalIdentification;
    private Integer reservedSpots;

    public Reservation(Integer id, Integer tripId, String personName, Integer userId, String personalIdentification, Integer reservedSpots) {
        super(id);
        this.tripId = tripId;
        this.personName = personName;
        this.userId = userId;
        this.personalIdentification = personalIdentification;
        this.reservedSpots = reservedSpots;
    }

    public Integer getTripId() {
        return tripId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPersonalIdentification() {
        return personalIdentification;
    }

    public void setPersonalIdentification(String personalIdentification) {
        this.personalIdentification = personalIdentification;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }
}
