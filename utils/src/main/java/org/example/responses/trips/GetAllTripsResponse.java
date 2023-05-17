package org.example.responses.trips;

import org.example.domain.TripDto;
import org.example.utils.Response;
import java.util.List;

public class GetAllTripsResponse implements Response {
    private List<TripDto> tripsDto;

    public GetAllTripsResponse(List<TripDto> trips){
        this.tripsDto=trips;
    }

    public List<TripDto> getTripsDto() {
        return tripsDto;
    }
}
