package com.badkultech.trip_management.service;

import com.badkultech.trip_management.dto.TripRequestDto;
import com.badkultech.trip_management.dto.TripResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TripService {
    TripResponseDto createTrip(TripRequestDto dto);
    Page<TripResponseDto> getAllTrips(int page, int size, String sort);
    TripResponseDto getTripById(Integer id);
    TripResponseDto updateTrip(Integer id, TripRequestDto dto);
    void deleteTrip(Integer id);
    Page<TripResponseDto> searchByDestination(String destination, int page, int size, String sort);
    List<TripResponseDto> filterByStatus(String status);
    List<TripResponseDto> getTripsBetween(LocalDate start, LocalDate end);
    Map<String, Object> getSummary();
}
