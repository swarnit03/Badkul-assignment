package com.badkultech.trip_management.util;

import com.badkultech.trip_management.dto.TripRequestDto;
import com.badkultech.trip_management.dto.TripResponseDto;
import com.badkultech.trip_management.entity.Status;
import com.badkultech.trip_management.entity.Trip;

public final class TripMapper {

    private TripMapper() {}

    public static Trip toEntity(TripRequestDto dto) {
        if (dto == null) return null;
        Trip t = new Trip();
        t.setDestination(dto.getDestination());
        t.setStartDate(dto.getStartDate());
        t.setEndDate(dto.getEndDate());
        t.setPrice(dto.getPrice());
        try {
            t.setStatus(Status.valueOf(dto.getStatus().trim().toUpperCase()));
        } catch (Exception e) {
            t.setStatus(Status.PLANNED);
        }
        return t;
    }

    public static TripResponseDto toResponseDto(Trip t) {
        if (t == null) return null;
        TripResponseDto r = new TripResponseDto();
        r.setId(t.getId());
        r.setDestination(t.getDestination());
        r.setStartDate(t.getStartDate());
        r.setEndDate(t.getEndDate());
        r.setPrice(t.getPrice());
        r.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        return r;
    }
}
