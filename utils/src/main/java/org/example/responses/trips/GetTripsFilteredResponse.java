package org.example.responses.trips;

import org.example.domain.TripDto;
import org.example.utils.Response;

import java.util.List;

public class GetTripsFilteredResponse implements Response {
    private final List<TripDto> tripsDto;

    public GetTripsFilteredResponse(List<TripDto> tripsDto) {
        this.tripsDto = tripsDto;
    }

    public List<TripDto> getTripsDto() {
        return tripsDto;
    }
}
